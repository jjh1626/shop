package com.example.shop.controller;

import com.example.shop.common.captcha.NaverCaptcha;
import com.example.shop.common.util.CommonUtils;
import com.example.shop.model.Attach;
import com.example.shop.model.Board;
import com.example.shop.model.BoardSearch;
import com.example.shop.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping(value = "/boards")
    public String boards(Model model) {
        BoardSearch boardSearch = new BoardSearch();
        model.addAttribute("boardSearch", boardSearch);
        return "board/boards";
    }

    @GetMapping(value = "/getBoardList")
    @ResponseBody
    public List<Board> getBoardList(@ModelAttribute("boardSearch") BoardSearch boardSearch) {
        log.info(">>>"+boardSearch.getType());
        log.info(">>>"+boardSearch.getKeyword());
        List<Board> result = boardService.findAll(boardSearch);
        return result;
    }

    @PostMapping(value = "/getPagingBoardList")
    @ResponseBody
    public Map<String, Object> getPagingBoardList(@ModelAttribute("boardSearch") BoardSearch boardSearch) {
        boardSearch.setTotal(boardService.getTotal(boardSearch));
        boardSearch.getPaging();
        List<Board> list = boardService.findAll(boardSearch);
        Map<String, Object> map = new HashMap<>();
        map.put("total", boardSearch.getTotal());
        map.put("list", list);
        return map;
    }

    @GetMapping(value = "/boards/view/{idx}")
    public String detail(@PathVariable("idx") Long idx, Model model) {
        Board board = boardService.findOne(idx);
        boardService.modifyHit(board);
        List<Attach> fileList = boardService.findFileList(board.getIdx());
        model.addAttribute("board", board);
        model.addAttribute("fileList", fileList);
        return "board/boardDetail";
    }

    @GetMapping(value = "/boards/write")
    public String boardWriteForm(Model model) {
        Board board = new Board();
        model.addAttribute("board", board);
        return "board/boardWrite";
    }

    @PostMapping(value = "/boards/write")
    public String boardWrite(@Valid Board board, BindingResult result
            , @RequestParam Map<String,Object> commandMap
            , Model model, HttpServletRequest request) throws Exception {

        //기존 파일(이미지,음성) 삭제 처리
        String dirPath = "C:"+ File.separator+"captchaFile"+File.separator;
        String fileName = (String)commandMap.get("captchaFileName");
        if(fileName!=null && !"".equals(fileName)) {
            NaverCaptcha.captchaFileRemove(fileName, dirPath);
        }

        //입력 검증
        if (result.hasErrors()) {
            return "board/boardWrite";
        }

        //캡차 확인
        String ccType = (String)commandMap.get("ccType");
        String key = (String)commandMap.get("key");
        String nImgText = (String)commandMap.get("nImgText");
        String nResult = null;
        if(ccType != null && ccType.equals("S")) {
            nResult = NaverCaptcha.captchaSkeyResult(key, nImgText);
        } else {
            nResult = NaverCaptcha.captchaNkeyResult(key, nImgText);
        }
        if(nResult==null) {
            model.addAttribute("msg","자동등록방지 텍스트가 일치하지 않습니다.");
            return "board/boardWrite";
        } else {
            if(!"true".equals(nResult)) {
                model.addAttribute("msg","자동등록방지 텍스트가 일치하지 않습니다.");
                return "board/boardWrite";
            }
        }

        //저장 및 수정
        if(board.getIdx() == null){
            boardService.save(board,request);
        } else {
            boardService.modify(board,request);
        }

        return "redirect:/boards";
    }

    @GetMapping(value = "/boards/editWrite")
    public String editBoardWriteFrom(Model model) {
        Board board = new Board();
        model.addAttribute("board", board);
        return "board/editBoardWrite";
    }

    @PostMapping(value = "/boards/editWrite")
    public String editBoardWrite(@Valid Board board, BindingResult result
            , @RequestParam Map<String,Object> commandMap
            , HttpServletRequest request) throws Exception {

        //입력 검증
        if (result.hasErrors()) {
            return "board/editBoardWrite";
        }

        //저장 및 수정
        if(board.getIdx() == null){
            boardService.save(board,request);
        } else {
            boardService.modify(board,request);
        }

        return "redirect:/boards";
    }

    @GetMapping(value = "/boards/edit/{idx}")
    public String boardEdit(@PathVariable("idx") Long idx, Model model) {
        Board board = boardService.findOne(idx);
        List<Attach> fileList = boardService.findFileList(board.getIdx());
        model.addAttribute("board", board);
        model.addAttribute("fileList", fileList);
        return "board/boardWrite";
    }

    @PostMapping(value = "/boardDeletePro")
    @ResponseBody
    public Map<String,Object> boardDeletePro(@RequestParam Map<String,Object> commandMap){
        String arrCheck = commandMap.get("arrCheck").toString();
        String[] strArr = arrCheck.split(",");
        String result = "success";
        try{
            for (String str : strArr) {
                boardService.boardDeleteOne(Long.parseLong(str));
            }
        } catch (Exception e){
            result = "fail";
        }
        Map<String, Object> map = new HashMap<>();
        map.put("result", result);
        return map;
    }
}

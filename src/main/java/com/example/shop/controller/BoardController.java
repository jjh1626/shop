package com.example.shop.controller;

import com.example.shop.model.Board;
import com.example.shop.model.BoardSearch;
import com.example.shop.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public List<Board> getBoardList(@ModelAttribute("boardSearch") BoardSearch boardSearch, Model model) {
        log.info(">>>"+boardSearch.getType());
        log.info(">>>"+boardSearch.getKeyword());
        List<Board> result = boardService.findAll(boardSearch);
        return result;
    }

    @PostMapping(value = "/getPagingBoardList")
    @ResponseBody
    public Map<String, Object> getPagingBoardList(@ModelAttribute("boardSearch") BoardSearch boardSearch, Model model) {
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
        model.addAttribute("board", board);
        return "board/boardDetail";
    }

    @GetMapping(value = "/boards/write")
    public String boardWriteForm(Model model) {
        Board board = new Board();
        model.addAttribute("board", board);
        return "board/boardWrite";
    }

    @PostMapping(value = "/boards/write")
    public String boardWrite(@Valid Board board, BindingResult result) {

        if (result.hasErrors()) {
            return "board/boardWrite";
        }

        if(board.getIdx() == null){
            boardService.save(board);
        } else {
            boardService.modify(board);
        }

        return "redirect:/boards";
    }

    @GetMapping(value = "/boards/edit/{idx}")
    public String boardEdit(@PathVariable("idx") Long idx, Model model) {
        Board board = boardService.findOne(idx);
        model.addAttribute("board", board);
        return "board/boardWrite";
    }
}

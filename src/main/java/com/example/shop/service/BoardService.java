package com.example.shop.service;

import com.example.shop.common.util.FileUtils;
import com.example.shop.model.Board;
import com.example.shop.model.BoardSearch;
import com.example.shop.model.Attach;
import com.example.shop.repository.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoardService {

    @Autowired BoardMapper boardMapper;
    @Autowired FileUtils fileUtils;

    @Transactional
    public void save(Board board, HttpServletRequest request) throws Exception {
        //게시물 등록
        boardMapper.save(board);

        //첨부파일 처리
        Map<String,Object> map = new HashMap<>();
        map.put("idx", board.getIdx());
        List<Map<String,Object>> files = fileUtils.parseInsertFileInfo(map, request);   //파일 저장
        for (Map<String, Object> file : files) {
            Attach attach = new Attach();
            attach.setBoardIdx((Long)file.get("board_idx"));
            attach.setOriginalFileName((String)file.get("original_file_name"));
            attach.setStoredFileName((String)file.get("stored_file_name"));
            attach.setFileSize((Long)file.get("file_size"));
            boardMapper.saveAttach(attach);     //DB 저장
        }
    }

    public Board findOne(Long idx) {
        Board board = boardMapper.findOne(idx);
        return board;
    }

    public List<Board> findAll(BoardSearch boardSearch) {
        List<Board> boards = boardMapper.findAll(boardSearch);
        return boards;
    }

    @Transactional
    public void modify(Board board) {
        boardMapper.modify(board);
    }

    @Transactional
    public void modifyHit(Board board) {
        boardMapper.modifyHit(board);
    }

    public Integer getTotal(BoardSearch boardSearch) {
        return boardMapper.getTotal(boardSearch);
    }
}

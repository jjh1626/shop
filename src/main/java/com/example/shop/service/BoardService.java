package com.example.shop.service;

import com.example.shop.model.Board;
import com.example.shop.model.BoardSearch;
import com.example.shop.repository.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {

    @Autowired BoardMapper boardMapper;

    @Transactional
    public void save(Board board) {
        boardMapper.save(board);
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
}

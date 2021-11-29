package com.example.shop.repository;

import com.example.shop.model.Board;
import com.example.shop.model.BoardSearch;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    void save(Board board);
    Board findOne(Long idx);
    List<Board> findAll(BoardSearch boardSearch);
    void modify(Board board);
    void modifyHit(Board board);
    Integer getTotal();
}

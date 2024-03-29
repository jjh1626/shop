package com.example.shop.repository;

import com.example.shop.model.Attach;
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
    Integer getTotal(BoardSearch boardSearch);
    void deleteOne(Long idx);

    void saveAttach(Attach attach);
    List<Attach> findFileList(Long boardIdx);
    Attach findFileOne(Long idx);
}

package com.example.shop.repository;

import com.example.shop.model.Item;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemMapper {
    void save(Item item);
    Item findOne(Long itemId);
    List<Item> findAll();
    void modify(Item item);
    void remove(Long itemId);
}

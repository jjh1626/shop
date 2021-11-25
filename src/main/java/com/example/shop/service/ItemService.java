package com.example.shop.service;

import com.example.shop.model.Item;
import com.example.shop.repository.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired ItemMapper itemMapper;

    //상품 저장
    public Long saveItem(Item item) {
        itemMapper.save(item);
        return item.getItemId();
    }

    //상품 리스트 조회
    public List<Item> findAll() {
        List<Item> items = itemMapper.findAll();
        return items;
    }

    //상품 조회
    public Item findOne(Long itemId) {
        Item item = itemMapper.findOne(itemId);
        return item;
    }

    //상품 수정
    public void updateItem(Item item) {
        itemMapper.modify(item);
    }
}

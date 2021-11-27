package com.example.shop.repository;

import com.example.shop.model.Order;
import com.example.shop.model.OrderSearch;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    void save(Order order);
    Order findOne(Long orderId);
    List<Order> findAll(OrderSearch orderSearch);
    void modifyStatus(Order order);
}

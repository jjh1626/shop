package com.example.shop.repository;

import com.example.shop.model.Item;
import com.example.shop.model.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    void save(OrderItem orderItem);
    OrderItem findOne(Long orderItemId);
    List<OrderItem> findByOrderId(Long orderId);
}

package com.example.shop.controller;

import com.example.shop.model.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class OrderForm {
    private Long orderId;
    private LocalDateTime orderDate;
    private OrderStatus status;

    //회원명
    private String memberName;
    //대표삼품명
    private String orderItemName;
    //대표상품 주문가격
    private int orderItemPrice;
    //대표상품 주문수량
    private int orderItemCount;
}

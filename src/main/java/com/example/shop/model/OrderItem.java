package com.example.shop.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderItem {
    private Long orderItemId;
    private Long itemId;
    private Long orderId;
    private int orderPrice;
    private int count;
}

package com.example.shop.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class Order {
    private Long orderId;
    private Long memberId;
    private Long deliveryId;
    private LocalDateTime orderDate;
    private OrderStatus status;
}

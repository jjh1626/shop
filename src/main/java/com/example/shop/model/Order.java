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

    public static Order createOrder(Long memberId, Long deliveryId) {
        Order order = new Order();
        order.setMemberId(memberId);
        order.setDeliveryId(deliveryId);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.ORDER);
        return order;
    }
}

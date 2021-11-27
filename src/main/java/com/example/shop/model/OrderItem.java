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

    public static OrderItem createOrderItem(Long itemId, Long orderId, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItemId(itemId);
        orderItem.setOrderId(orderId);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);
        return orderItem;
    }
}

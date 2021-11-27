package com.example.shop.service;

import com.example.shop.model.*;
import com.example.shop.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final MemberMapper memberMapper;
    private final OrderMapper orderMapper;
    private final ItemMapper itemMapper;
    private final DeliveryMapper deliveryMapper;
    private final OrderItemMapper orderItemMapper;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        //엔티티 조회 (회원, 아이템)
        Member member = memberMapper.findOne(memberId);
        Item item = itemMapper.findOne(itemId);

        //배송정보 생성, 배송정보 저장
        Delivery delivery = Delivery.createDelivery(member.getCity(), member.getStreet(), member.getZipcode());
        deliveryMapper.save(delivery);

        //주문 생성, 주문 저장
        Order order = Order.createOrder(memberId, delivery.getDeliveryId());
        orderMapper.save(order);

        //주문 아이템 생성, 주문 아이템 저장
        OrderItem orderItem = OrderItem.createOrderItem(item.getItemId(), order.getOrderId(), item.getPrice(), count);
        orderItemMapper.save(orderItem);

        //아이템 재고 감소
        item.removeStock(count);
        itemMapper.modifyStock(item);

        return order.getOrderId();
    }


    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        //엔티티 조회 (주문, 아이템)
        Order order = orderMapper.findOne(orderId);
        List<OrderItem> orderItems = orderItemMapper.findByOrderId(order.getOrderId());

        //주문 취소
        order.setStatus(OrderStatus.CANCEL);
        orderMapper.modifyStatus(order);

        //아이템 재고 증가
        for (OrderItem orderItem : orderItems) {
            Item item = itemMapper.findOne(orderItem.getItemId());
            item.addStock(orderItem.getCount());
            itemMapper.modifyStock(item);
        }
    }

    /**
     * 주문 검색
     */
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderMapper.findAll(orderSearch);
    }

}

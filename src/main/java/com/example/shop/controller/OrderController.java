package com.example.shop.controller;

import com.example.shop.model.*;
import com.example.shop.repository.OrderItemMapper;
import com.example.shop.service.ItemService;
import com.example.shop.service.MemberService;
import com.example.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;
    private final OrderItemMapper orderItemMapper;

    @GetMapping(value = "/order")
    public String createOrderForm(Model model) {
        List<Member> members = memberService.findAll();
        List<Item> items = itemService.findAll();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping(value = "/order")
    public String createOrder(@RequestParam Map<String, String> param) {
        Long memberId = Long.parseLong(param.get("memberId"));
        Long itemId = Long.parseLong(param.get("itemId"));
        int count = Integer.parseInt(param.get("count"));
        Long orderId = orderService.order(memberId, itemId, count);

        return "redirect:/orders";
    }

    @GetMapping(value = "/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        List<Order> orders = orderService.findOrders(orderSearch);

        List<OrderForm> orderForms = new ArrayList<>();
        for (Order order : orders) {
            OrderForm orderForm = new OrderForm();
            orderForm.setOrderId(order.getOrderId());
            orderForm.setOrderDate(order.getOrderDate());
            orderForm.setStatus(order.getStatus());
            orderForm.setMemberName(memberService.findOne(order.getMemberId()).getName());

            List<OrderItem> orderItems = orderItemMapper.findByOrderId(order.getOrderId());
            OrderItem orderItem = orderItems.get(0);
            Item item = itemService.findOne(orderItem.getItemId());
            orderForm.setOrderItemName(item.getName());
            orderForm.setOrderItemPrice(orderItems.get(0).getOrderPrice());
            orderForm.setOrderItemCount(orderItems.get(0).getCount());
            orderForms.add(orderForm);
        }

        model.addAttribute("orders", orderForms);
        return "order/orderList";
    }

    @PostMapping(value = "/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}

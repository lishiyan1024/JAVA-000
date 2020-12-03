package com.nero.demo.controller;

import com.nero.demo.model.Order;
import com.nero.demo.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/createOrder")
    public String createOrder(@RequestBody Order order) {
        return orderService.createOrder(order) > 0 ? "创建订单成功" : "创建订单失败";
    }

    @GetMapping("/order")
    public Order getOrder(String orderNo) {
        return orderService.getOrderByOrderNo(orderNo);
    }
}

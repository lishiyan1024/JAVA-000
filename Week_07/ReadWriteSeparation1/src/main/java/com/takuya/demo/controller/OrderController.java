package com.takuya.demo.controller;

import com.takuya.demo.model.Order;
import com.takuya.demo.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/createOrder")
    public String createOrder(@RequestBody Order order) {
        return orderService.createOrder(order) > 0 ? "创建订单成功" : "创建订单失败";
    }

    @GetMapping("/orders")
    public List<Order> orders() {
        return orderService.orders();
    }
}

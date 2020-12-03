package com.takuya.demo.service;

import com.takuya.demo.mapper.OrderReadDao;
import com.takuya.demo.mapper.OrderWriteDao;
import com.takuya.demo.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderWriteDao orderWriteDao;

    @Autowired
    OrderReadDao orderReadDao;

    public int createOrder(Order order) {
        return orderWriteDao.insert(order);
    }

    public List<Order> orders() {
        return orderReadDao.getList();
    }
}

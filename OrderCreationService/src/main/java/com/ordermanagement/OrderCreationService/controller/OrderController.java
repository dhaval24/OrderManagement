package com.ordermanagement.OrderCreationService.controller;

import com.ordermanagement.OrderCreationService.domain.Order;
import com.ordermanagement.OrderCreationService.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService service;

    @PostMapping
    public void placeOrder(@RequestBody Order order) {
        try {
            service.sendOrder(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

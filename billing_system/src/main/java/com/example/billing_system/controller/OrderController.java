package com.example.billing_system.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.billing_system.entities.Product;
import com.example.billing_system.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService oderService;

    public OrderController(OrderService oderService) {
        this.oderService = oderService;
    }

    @PostMapping("/newBill")
    public void postMethodName(@RequestBody List<Product> orders) {
        // List<Order> orders = new ArrayList<>();
        // orders.add(order);
        oderService.writeOrdersToCsv(orders);
    }
}

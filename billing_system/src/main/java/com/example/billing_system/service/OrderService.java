package com.example.billing_system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.billing_system.entities.Order;

@Service
public class OrderService {

    private List<Order> orders;

    public Float processBill() {
        Float invoice = 0f;
        for (Order order : orders) {
            invoice = order.getProductPrice() * order.getQuantity();
        }
        return invoice;
    }
}

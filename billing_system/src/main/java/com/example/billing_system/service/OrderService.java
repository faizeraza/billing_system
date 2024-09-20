package com.example.billing_system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.billing_system.entities.Order;

@Service
public class OrderService {

    private List<Order> orders;

    public Float processBill() {
        Float invoice = 0f;
        Float totalPrice;
        for (Order order : orders) {
            totalPrice = order.getProductPrice() * order.getQuantity();
            
            invoice+=totalPrice;
        }
        return invoice;
    }
}

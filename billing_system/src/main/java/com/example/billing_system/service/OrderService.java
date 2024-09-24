package com.example.billing_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.billing_system.entities.Product;

@Service
public class OrderService {

    @Autowired
    private csvGenerator generator;
    private List<Product> orders;

    public double processBill() {
        double invoice = 0f;
        double totalPrice;
        for (Product order : orders) {
            totalPrice = order.getPrice();

            invoice += totalPrice;
        }
        return invoice;
    }

    public void writeOrdersToCsv(List<Product> orders) {
        for (Product order : orders) {
            generator.writeOrderToCsv(order);
        }
    }
}

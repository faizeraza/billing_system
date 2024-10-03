package com.example.billing_system.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class Product {

    private int productId;
    private String productName;
    private double price;
    private String unit;
    private int quantity;

    public Double getAmount() {
        return quantity * price;
    }

    @Override
    public String toString() {
        return productId + "," + productName + "," + price + "," + quantity;
    }
}

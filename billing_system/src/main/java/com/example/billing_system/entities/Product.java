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
    private double unitPrice;
    private int quantity;

    public Double getPrice() {
        return quantity * unitPrice;
    }

    @Override
    public String toString() {
        return productId + "," + productName + "," + unitPrice + "," + quantity;
    }
}

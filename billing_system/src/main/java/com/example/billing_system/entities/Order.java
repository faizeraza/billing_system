package com.example.billing_system.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class Order {

    private String customerName;
    private String productName;
    private Float productPrice;
    private Float quantity;
}

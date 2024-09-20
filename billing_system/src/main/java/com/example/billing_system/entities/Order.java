package com.example.billing_system.entities;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@Table
public class Order {

    private String customerName;
    private String productName;
    private Float productPrice;
    private Float quantity;
}

package com.example.billing_system.entities;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Invoice {

    private Customer customer;
    private List<Product> product;
}

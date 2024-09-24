package com.example.billing_system.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.billing_system.entities.Customer;
import com.example.billing_system.service.GenerateBill;

@RestController
@RequestMapping("api/v1/getBill")
public class GenerateBillController {

    private final GenerateBill generateBill;

    public GenerateBillController(GenerateBill generateBill) {
        this.generateBill = generateBill;
    }

    @PostMapping()
    public void postMethodName(@RequestBody Customer customer) {
        try {
            generateBill.generateBill(customer);
        } catch (Exception ex) {
            System.out.println("Error" + ex);
        }
    }

}

package com.example.billing_system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.billing_system.entities.Invoice;
import com.example.billing_system.service.GenerateBill;

@RestController

@RequestMapping("/order")
public class OrderController {

    private final GenerateBill generateBill;

    public OrderController(GenerateBill generateBill1) {
        this.generateBill = generateBill1;
    }

    @GetMapping("/")
    public String getMethodName(Model model) {
        return "index";
    }

    @PostMapping("/newBill")
    public ResponseEntity<String> postMethodName(@RequestBody Invoice invoice) {
        try {
            generateBill.generateBill(invoice);
            return ResponseEntity.ok("Bill Generated Successfully!!!");
        } catch (Exception ex) {
            return ResponseEntity.ok("Bill Not Generated !!! " + ex.getMessage());
        }
    }
}

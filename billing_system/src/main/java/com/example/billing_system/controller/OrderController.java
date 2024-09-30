package com.example.billing_system.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(path = "/download", method = RequestMethod.GET)
    public ResponseEntity<Resource> requestMethodName(@RequestBody Invoice invoice) throws IOException, Exception {
        File file = new File("src/main/resources/bill.pdf");
        System.out.println("Invoice" + invoice);
        generateBill.generateBill(invoice);
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bill.pdf");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
}

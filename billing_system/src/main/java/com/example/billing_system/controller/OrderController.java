package com.example.billing_system.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

    @PostMapping("/download")
    public ResponseEntity<Object> downloadInvoice(@RequestBody Invoice invoice) throws IOException, Exception {
        HttpHeaders headers = new HttpHeaders();
        File file = new File("src/main/resources/bill.pdf");
        Path path;
        ByteArrayResource resource = null;
        try {
            // Generate the bill and save it as PDF
            generateBill.generateBill(invoice);

            // Prepare file for download
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bill.pdf");  // Sets the filename for download
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");

            path = Paths.get(file.getAbsolutePath());
            System.out.println("here we go : " + path);
            resource = new ByteArrayResource(Files.readAllBytes(path));
            System.out.println("File" + resource.toString());
        } catch (Exception e) {
            return ResponseEntity.ok("Something Went Wrong");
        }
        // Return the file as a downloadable response
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}

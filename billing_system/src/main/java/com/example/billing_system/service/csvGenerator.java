package com.example.billing_system.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.example.billing_system.entities.Order;

@Service
public class csvGenerator {

    
        // Method to write orders to a CSV file using BufferedWriter
        public void writeOrderToCsv(Order order) {
            String filePath="/home/admin/Desktop/Billing_System/billing_system/billing_system/src/main/resources/orderList.csv";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                // Write order details
                writer.write(order.toString());
                writer.newLine();  // Moves to the next line for the next order
        
                System.out.println("Order has been appended to the CSV file successfully!");
        
            } catch (IOException e) {
                System.out.println("Error while writing to CSV: " + e.getMessage());
            }
        }
        
    
    }

    


package com.example.billing_system.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.billing_system.entities.Customer;
import com.example.billing_system.entities.Product;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@Service
public class GenerateBill {

    public void generateBill(Customer customer) throws Exception {
        List<Product> products = readProductsFromCSV("/home/admin/Desktop/projects/billing_system/billing_system/src/main/resources/orderList.csv");
        Document document = new Document();
        try (OutputStream outputStream = new FileOutputStream(new File("/home/admin/Desktop/projects/billing_system/billing_system/src/main/resources/bill.pdf"))) {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Add Title
            document.add(new Paragraph("Billing Invoice", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));

            // Add Customer Details
            document.add(new Paragraph("Customer Name: " + customer.getCustomer()));
            document.add(new Paragraph("Customer No: " + customer.getMobileNumber()));
            // document.add(new Paragraph("Customer Email: " + customerEmail));
            document.add(new Paragraph("Date: " + new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
            document.add(new Paragraph("\n"));

            // Create Product Table
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setSpacingBefore(5f);
            table.setSpacingAfter(5f);

            // Table Header
            addTableHeader(table);
            double subtotal = 0;

            // Add Products to Table
            for (Product product : products) {
                addProductRow(table, product);
                subtotal += product.getUnitPrice() * product.getQuantity();
            }

            document.add(table);

            // Add Subtotal
            document.add(new Paragraph("\nSubtotal: $" + String.format("%.2f", subtotal), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            // Close Document
            document.close();
            // Clear csv
            clearCsv();
        }
    }

    public void clearCsv() {
        FileWriter writer;
        try {
            writer = new FileWriter("/home/admin/Desktop/projects/billing_system/billing_system/src/main/resources/orderList.csv", false);
            writer.write("");  // Writing an empty string to clear the file
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private List<Product> readProductsFromCSV(String csvFilePath) throws Exception {
        List<Product> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 4) { // Assuming CSV has 4 columns
                    int productId = Integer.parseInt(values[0].trim());
                    String productName = values[1].trim();
                    double unitPrice = Double.parseDouble(values[2].trim());
                    int quantity = Integer.parseInt(values[3].trim());
                    products.add(new Product(productId, productName, unitPrice, quantity));
                }
            }
        }
        return products;
    }

    private void addTableHeader(PdfPTable table) {
        String[] headers = {"Product ID", "Product Name", "Unit Price", "Quantity", "ProductTotal"};
        for (String headerTitle : headers) {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setPhrase(new Phrase(headerTitle));
            table.addCell(header);
        }
    }

    private void addProductRow(PdfPTable table, Product product) {
        table.addCell(String.valueOf(product.getProductId()));
        table.addCell(product.getProductName());
        table.addCell(String.format("%.2f", product.getUnitPrice()));
        table.addCell(String.valueOf(product.getQuantity()));
        table.addCell(String.valueOf(product.getPrice()));
    }
    // public static void main(String[] args) {
    //     GenerateBill billGenerator = new GenerateBill("John Doe", "123456", "john.doe@example.com", "/home/admin/Desktop/billing_system/billing_system/billing_system/src/main/resources/products.csv");
    //     try {
    //         billGenerator.generateBill("bill.pdf");
    //     } catch (Exception e) {
    //         // TODO Auto-generated catch block
    //         e.printStackTrace();
    //     }

    // }
}

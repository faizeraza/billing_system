package com.example.billing_system.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.billing_system.entities.Customer;
import com.example.billing_system.entities.Invoice;
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

    public void generateBill(Invoice invoice) throws Exception {
        List<Product> products = invoice.getProduct();
        Customer customer = invoice.getCustomer();
        Document document = new Document();
        try (OutputStream outputStream = new FileOutputStream(new File("src/main/resources/bill.pdf"))) {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Add Title
            document.add(new Paragraph("                                   Diamond Opticals                                   ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            System.out.println("Email, " + customer.getEmail());
            // Add Customer Details
            document.add(new Paragraph("Customer Name: " + customer.getName()));
            document.add(new Paragraph("Mobile No: " + customer.getMobileNumber()));
            document.add(new Paragraph("Address: " + customer.getAddress()));
            document.add(new Paragraph("Email: " + customer.getEmail()));

            // document.add(new Paragraph("Customer Email: " + customerEmail));
            document.add(new Paragraph("Date: " + new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
            document.add(new Paragraph("\n"));

            // Create Product Table
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setSpacingBefore(5f);
            table.setSpacingAfter(5f);

            // Table Header
            addTableHeader(table);
            double subtotal = 0;

            // Add Products to Table
            for (Product product : products) {
                addProductRow(table, product);
                subtotal += product.getPrice() * product.getQuantity();
            }

            document.add(table);

            // Add Subtotal
            document.add(new Paragraph("\nSubtotal: " + String.format("%.2f", subtotal), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            // Close Document
            document.close();
            // Clear csv
            // clearCsv();
        }
    }

    private void addTableHeader(PdfPTable table) {
        String[] headers = {"S.N", "Description of Goods", "Unit", "Quantity", "Price", "Amount"};
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
        table.addCell(product.getUnit());
        table.addCell(String.valueOf(product.getQuantity()));
        table.addCell(String.valueOf(product.getPrice()));
        table.addCell(String.format("%.2f", product.getAmount()));
    }

}

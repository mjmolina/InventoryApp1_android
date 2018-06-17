package com.example.mariajosemolina.bookstore1;

public class BookRegistry {

    public String productName;
    public double price;
    public int quantity;
    public String supplierName;
    public String supplierPhoneNumber;

    public BookRegistry(String productName, double price, int quantity, String supplierName, String supplierPhoneNumber) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.supplierName = supplierName;
        this.supplierPhoneNumber = supplierPhoneNumber;
    }

}
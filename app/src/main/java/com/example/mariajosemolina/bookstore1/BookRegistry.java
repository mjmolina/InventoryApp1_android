package com.example.mariajosemolina.bookstore1;

import java.io.Serializable;

public class BookRegistry implements Serializable {

    public int id;
    public String productName;
    public double price;
    public int quantity;
    public String supplierName;
    public String supplierPhoneNumber;

    public BookRegistry() {
        this.productName = "";
        this.price = 0;
        this.quantity = 0;
        this.supplierName = "";
        this.supplierPhoneNumber = "";
        this.id = -1;
    }

    public BookRegistry(String productName, double price, int quantity, String supplierName, String supplierPhoneNumber) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.supplierName = supplierName;
        this.supplierPhoneNumber = supplierPhoneNumber;
        this.id = -1;
    }

}
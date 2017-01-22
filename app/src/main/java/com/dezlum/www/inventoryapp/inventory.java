package com.dezlum.www.inventoryapp;

/**
 * Created by saurabh on 1/23/2017.
 */

public class inventory {
    private int id;
    private String productName;
    private int quantity;
    private double price;

    public inventory() {
        super();
    }

    public inventory(String productName, int quantity, double price) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;

    }

    public int getProductId() {
        return this.id;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String name) {
        this.productName = name;
    }
    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setProductID(int id) {
        this.id = id;
    }

    public void quantitySell() {
        this.quantity = this.quantity - 1;
        if (this.quantity < 0) {
            this.quantity = 0;
        }
    }

    @Override
    public String toString() {
        return "Product is Available :" + getProductName()+"\n"+ getPrice()+" "+getQuantity();
    }
}

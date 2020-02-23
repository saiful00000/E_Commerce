package com.example.e_commerce.models;

public class CartItem {
    private String brand;
    private String date;
    private String description;
    private String key;
    private String name;
    private String price;
    private String quantity;
    private String time;

    public CartItem() {
    }

    public CartItem(String brand, String date, String description, String key, String name, String price, String quantity, String time) {
        this.brand = brand;
        this.date = date;
        this.description = description;
        this.key = key;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.time = time;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

package com.example.e_commerce.models;

public class Product {
    private String name;
    private String brand;
    private String category;
    private String price;
    private String image;
    private String description;
    private String key;
    private String date;
    private String time;

    public Product() {
    }

    public Product(String name, String brand, String category, String price, String image, String description, String key, String date, String time) {
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.image = image;
        this.description = description;
        this.key = key;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

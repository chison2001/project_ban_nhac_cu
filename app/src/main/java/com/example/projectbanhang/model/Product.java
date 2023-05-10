package com.example.projectbanhang.model;

import java.io.Serializable;

public class Product implements Serializable {
    int id;
    String image;
    String name;
    Long price;
    String mota;
    String category;

    public Product(int id, String name, String image, Long price, String mota, String category) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;
        this.mota = mota;
        this.category = category;
    }

    public Product(String name, String image, Long price, String mota, String category) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.mota = mota;
        this.category = category;
    }

    public Product(String name, String image, Long price, String category) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

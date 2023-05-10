package com.example.projectbanhang.model;

public class Menu {
    String name;
    String image;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Menu(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public Menu() {
    }
}

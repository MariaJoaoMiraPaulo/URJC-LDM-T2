package com.example.mariajoaomirapaulo.myshoppinglist;

public class ProductItem {

    private String name;

    private String description;

    public ProductItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

package com.example.mariajoaomirapaulo.myshoppinglist;

public class ProductItem {

    private String name;

    private String photo = "";

    public ProductItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}

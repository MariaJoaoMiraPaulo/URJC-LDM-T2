package com.example.mariajoaomirapaulo.myshoppinglist;

public class HistoryList {

    private String productsList;


    public HistoryList() {

    }

    public HistoryList(String productList) {
        this.productsList = productList;
    }

    public String getProductsList() {
        return productsList;
    }

    public void setProductsList(String productsList) {
        this.productsList = productsList;
    }
}

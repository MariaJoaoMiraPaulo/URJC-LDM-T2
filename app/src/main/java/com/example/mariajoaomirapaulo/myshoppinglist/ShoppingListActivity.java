package com.example.mariajoaomirapaulo.myshoppinglist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class ShoppingListActivity extends AppCompatActivity{

    ArrayAdapter<ProductItem> adapter;
    ArrayList products = new ArrayList<ProductItem>();
    ListView listProducts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list);

        ImageButton addProduct = (ImageButton) findViewById(R.id.addIcon);
        listProducts = (ListView) findViewById(R.id.listProducts);

        populateProductList();

        //add new product
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Add new Item");
            }
        });

    }

    public void populateProductList(){

        //testing list view
        ProductItem bread = new ProductItem("bread");
        ProductItem book = new ProductItem("book");
        products.add(bread);
        products.add(book);

        ProductAdapter productAdapter = new ProductAdapter(ShoppingListActivity.this, products);
        listProducts.setAdapter(productAdapter);
    }
}

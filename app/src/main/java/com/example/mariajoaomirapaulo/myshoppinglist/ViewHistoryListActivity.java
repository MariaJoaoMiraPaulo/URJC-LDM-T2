package com.example.mariajoaomirapaulo.myshoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

public class ViewHistoryListActivity extends AppCompatActivity {

    AdminSQLiteOpenHelper databaseHelper;

    ListView listProducts;
    String historyProducts;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_history_list_layout);

        databaseHelper = new AdminSQLiteOpenHelper(this);

        listProducts = (ListView) findViewById(R.id.historyListProducts);

        Intent receivedIntent = getIntent();
        historyProducts = receivedIntent.getStringExtra("products");



        populateProductList();
    }

    public void populateProductList() {

        List<String> items = Arrays.asList(historyProducts.split("\\s*,\\s*"));


        //createAdapter
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listProducts.setAdapter(arrayAdapter);

    }
}

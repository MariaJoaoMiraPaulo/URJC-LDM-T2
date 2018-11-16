package com.example.mariajoaomirapaulo.myshoppinglist;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    AdminSQLiteOpenHelper databaseHelper;
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        databaseHelper = new AdminSQLiteOpenHelper(this);
        listView = (ListView) findViewById(R.id.listProducts);

        populateList();
    }

    public void populateList(){
        Cursor allProducts = databaseHelper.getAllLists();
        ArrayList<String> shoppingLists = new ArrayList<>();

        while (allProducts.moveToNext()) {
            shoppingLists.add(Integer.toString(allProducts.getInt(0))); // column index to get from the table
        }

        //createAdapter
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, shoppingLists);
        listView.setAdapter(arrayAdapter);
    }
}

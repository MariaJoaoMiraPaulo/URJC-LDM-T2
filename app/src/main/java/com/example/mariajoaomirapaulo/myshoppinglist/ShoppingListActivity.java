package com.example.mariajoaomirapaulo.myshoppinglist;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
                openDialog();
            }
        });

    }

    public void openDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New Product");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String productName = input.getText().toString();
                ProductItem product = new ProductItem(productName);
                addProduct(product);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        builder.show();
    }

    public void addProduct(ProductItem productItem){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "gestion", null, 1);
        SQLiteDatabase database = admin.getWritableDatabase();

        //add Product to database
    }

    public void populateProductList(){

        //testing list view To DELETE

        ProductItem bread = new ProductItem("bread");
        ProductItem book = new ProductItem("book");
        products.add(bread);
        products.add(book);


        //get all products from database

        ProductAdapter productAdapter = new ProductAdapter(ShoppingListActivity.this, products);
        listProducts.setAdapter(productAdapter);
    }
}

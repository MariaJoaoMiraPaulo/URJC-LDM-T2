package com.example.mariajoaomirapaulo.myshoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ViewProductActivity extends AppCompatActivity{

    private Button updateProduct, deleteProduct;
    private EditText newProductName;

    AdminSQLiteOpenHelper databaseHelper;

    private String productName;
    private int productId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_product_layout);

        updateProduct = (Button) findViewById(R.id.updateProduct);
        deleteProduct = (Button) findViewById(R.id.deleteProduct);
        newProductName = (EditText) findViewById(R.id.newProductName);
        databaseHelper = new AdminSQLiteOpenHelper(this, "products");

        Intent receivedIntent = getIntent();

        productId = receivedIntent.getIntExtra("id", -1);
        toastHelper("int " + productId);
        productName = receivedIntent.getStringExtra("name");

        newProductName.setText(productName);

        updateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = newProductName.getText().toString();

                if (newName.equals("")) {
                    toastHelper("The new product name can't be empty!");
                } else {
                    databaseHelper.updateProductName(productName, productId, newName);
                    toastHelper("Product name updated successfully!");
                }
            }
        });

        deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.deleteProduct(productName, productId);
                newProductName.setText("");
                toastHelper("Product deleted!");
            }
        });
    }

    private void toastHelper(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}

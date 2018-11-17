package com.example.mariajoaomirapaulo.myshoppinglist;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShoppingListActivity extends AppCompatActivity {

    AdminSQLiteOpenHelper databaseHelper;
    ProductAdapter productAdapter;
    ListView listProducts;
    SoundPool soundPool;

    int successSound;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.shopping_list);

        databaseHelper = new AdminSQLiteOpenHelper(this);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);
        successSound = soundPool.load(this, R.raw.success, 1);

        ImageButton addProduct = (ImageButton) findViewById(R.id.addIcon);
        listProducts = (ListView) findViewById(R.id.listProducts);
        ImageButton concludeList = (ImageButton) findViewById(R.id.conclude);

        populateProductList();

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(successSound, 1, 3, 1, 0, 0);
                openDialog();
            }
        });

        concludeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(successSound, 1, 3, 1, 0, 0);
                if (productAdapter.getCount() > 0)
                    concludeProductsList();
            }
        });

    }

    public void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New Product");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String productName = input.getText().toString();
                if (productName.length() == 0) {
                    toastHelper("You must specify the product name!");
                } else {
                    ProductItem product = new ProductItem(productName);
                    addProduct(product);
                    soundPool.play(successSound, 1, 3, 1, 0, 0);
                }
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

    public void concludeProductsList() {

        String products = "";
        ArrayList<String> productsList = getProducts();

        for (String product : productsList) {
            products += product + ",";
        }

        //remove last comma
        products = products.substring(0, products.length() - 1);
        databaseHelper.addList(products);
        databaseHelper.dropCurrentListTable();
        populateProductList();
    }

    public void addProduct(ProductItem product) {
        boolean addProduct = databaseHelper.addProduct(product);

        if (addProduct) {
            populateProductList();
            toastHelper("Product successfully added!");
        } else {
            toastHelper("Something went wrong adding the product...");
        }

    }

    public ArrayList<String> getProducts() {
        Cursor allProducts = databaseHelper.getAllProducts();
        ArrayList<String> productsList = new ArrayList<>();

        while (allProducts.moveToNext()) {
            productsList.add(allProducts.getString(1)); // column index to get from the table
        }

        return productsList;
    }

    public void populateProductList() {

        ArrayList<String> productsList = getProducts();

        productAdapter = new ProductAdapter(ShoppingListActivity.this, productsList);
        listProducts.setAdapter(productAdapter);

        //on item click listener
        listProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                soundPool.play(successSound, 1, 3, 1, 0, 0);
                String productName = adapterView.getItemAtPosition(position).toString(); // Gets the clicked item

                Intent viewProductIntent = new Intent(ShoppingListActivity.this, ViewProductActivity.class);
                Cursor product = databaseHelper.getProductId(productName);
                int productId = -1;
                while (product.moveToNext()) {
                    productId = product.getInt(0);
                }

                viewProductIntent.putExtra("id", productId);
                viewProductIntent.putExtra("name", productName);

                startActivity(viewProductIntent);

            }
        });

    }

    private void toastHelper(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateProductList();
    }
}

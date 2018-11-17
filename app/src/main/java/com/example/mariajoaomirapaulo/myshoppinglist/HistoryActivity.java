package com.example.mariajoaomirapaulo.myshoppinglist;

import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    AdminSQLiteOpenHelper databaseHelper;
    ListView listView;
    SoundPool soundPool;

    int successSound;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        databaseHelper = new AdminSQLiteOpenHelper(this);
        listView = (ListView) findViewById(R.id.listProducts);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);
        successSound = soundPool.load(this, R.raw.success, 1);

        populateList();
    }

    public ArrayList<String> getProducts() {
        Cursor allProducts = databaseHelper.getAllLists();
        ArrayList<String> productsList = new ArrayList<>();
        if (allProducts.moveToFirst()) {

            do {
                productsList.add(allProducts.getString(2));

            } while (allProducts.moveToNext());
        }

        return productsList;
    }

    public void populateList(){

        ArrayList<String> productsList = getProducts();

        //createAdapter
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, productsList);
        listView.setAdapter(arrayAdapter);

        //on item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                soundPool.play(successSound, 1, 3, 1, 0, 0);
                String historyListId = adapterView.getItemAtPosition(position).toString(); // Gets the clicked item

                Intent viewHistoryIntent = new Intent(HistoryActivity.this, ViewHistoryListActivity.class);
                Cursor cursor = databaseHelper.getListByDate(historyListId);

                String products = null;
                if (cursor.moveToFirst()) {

                    do {
                        products = cursor.getString(1);

                    } while (cursor.moveToNext());
                }
                viewHistoryIntent.putExtra("products", products);

                startActivity(viewHistoryIntent);

            }
        });
    }
}

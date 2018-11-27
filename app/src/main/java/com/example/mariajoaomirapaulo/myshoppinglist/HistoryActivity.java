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

/**
 * The history activity class where you can see your past shopping lists.
 */
public class HistoryActivity extends AppCompatActivity {

    AdminSQLiteOpenHelper databaseHelper;
    ListView productsList;

    // The sound pool and the success sound.
    SoundPool soundPool;
    int successSound;

    /**
     * On create method.
     * Populates the history list adapter.
     *
     * @param savedInstanceState saved instance state
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        // Changed action bar image
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_action_bar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        databaseHelper = new AdminSQLiteOpenHelper(this);
        productsList = (ListView) findViewById(R.id.listProducts);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);
        successSound = soundPool.load(this, R.raw.success, 1);

        populateList();
    }

    /**
     * Gets all the past shopping lists.
     *
     * @return past shopping lists
     */
    public ArrayList<String> getHistoryList() {
        Cursor cursor = databaseHelper.getAllLists();
        ArrayList<String> historyList = new ArrayList<>();
        if (cursor.moveToFirst()) {

            do {
                historyList.add(cursor.getString(2));

            } while (cursor.moveToNext());
        }

        return historyList;
    }

    /**
     * Populates the adapter with the past shopping lists.
     * Listens for a click on each single item of the list to launch a single past shopping list intent.
     */
    public void populateList() {

        ArrayList<String> historyList = getHistoryList();

        //createAdapter
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, historyList);
        productsList.setAdapter(arrayAdapter);

        //on item click listener
        productsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                // Play the success sound from the sound pool
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

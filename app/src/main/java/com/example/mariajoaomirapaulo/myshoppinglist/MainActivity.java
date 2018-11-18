package com.example.mariajoaomirapaulo.myshoppinglist;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * The main activity class.
 * Where the user can navigate to the most important screens.
 */
public class MainActivity extends AppCompatActivity {

    SoundPool soundPool;
    int successSound;

    /**
     * Creates the listeners for all the buttons.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button shoppingList = (Button) findViewById(R.id.shopping_list);
        Button manual = (Button) findViewById(R.id.manual);
        Button history = (Button) findViewById(R.id.history);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);
        successSound = soundPool.load(this, R.raw.success, 1);

        // Go to the current shopping list page
        shoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(successSound, 1, 3, 1, 0, 0);
                Intent intent = new Intent(MainActivity.this, ShoppingListActivity.class);
                startActivity(intent);
            }
        });

        // Go to the past shopping lists page
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(successSound, 1, 3, 1, 0, 0);
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        // Go to the manual page
        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(successSound, 1, 3, 1, 0, 0);
                Intent intent = new Intent(MainActivity.this, UserManualActivity.class);
                startActivity(intent);
            }
        });
    }
}

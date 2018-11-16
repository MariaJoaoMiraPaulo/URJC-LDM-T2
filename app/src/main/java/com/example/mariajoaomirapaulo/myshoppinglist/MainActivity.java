package com.example.mariajoaomirapaulo.myshoppinglist;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    SoundPool soundPool;
    int successSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button shoppingList = (Button) findViewById(R.id.shopping_list);
        Button manual = (Button) findViewById(R.id.manual);
        Button history = (Button) findViewById(R.id.history);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);
        successSound = soundPool.load(this,R.raw.success, 1);

        shoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(successSound, 1, 3, 1, 0, 0);
                Intent intent = new Intent(MainActivity.this, ShoppingListActivity.class);
                startActivity(intent);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(successSound, 1, 3, 1, 0, 0);
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

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

package com.example.mariajoaomirapaulo.myshoppinglist;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter{

    private Activity context;
    private ArrayList<String> list;
    SoundPool soundPool;
    int checkSound;

    public ProductAdapter(Activity context, ArrayList<String> list) {
        this.context = context;
        this.list = list;

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);
        checkSound = soundPool.load(context,R.raw.check, 1);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item, parent, false);
        }

        String productItem = (String) getItem(position);
        TextView name = (TextView) convertView.findViewById(R.id.productName);

        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            soundPool.play(checkSound, 1, 3, 1, 0, 0);
            }
        });

        name.setText(productItem);

        return convertView;
    }
}

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

/**
 * The product adapter class.
 * Implements the required methods to show the product adapter in the products list.
 */
public class ProductAdapter extends BaseAdapter {

    private Activity context;
    private ArrayList<String> list;
    SoundPool soundPool;
    int checkSound;

    /**
     * The Product Adapter constructor.
     *
     * @param context the context
     * @param list    the product list
     */
    public ProductAdapter(Activity context, ArrayList<String> list) {
        this.context = context;
        this.list = list;

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);
        checkSound = soundPool.load(context, R.raw.check, 1);
    }

    /**
     * Gets the amount of products.
     *
     * @return the list size
     */
    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * Gets a product in the specified position.
     *
     * @param position the position
     * @return the product
     */
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    /**
     * Gets the item id.
     *
     * @param position the position
     * @return 0
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Gets the new view.
     *
     * @param position    the item position
     * @param convertView the view
     * @param parent      the parent
     * @return new converted view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
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

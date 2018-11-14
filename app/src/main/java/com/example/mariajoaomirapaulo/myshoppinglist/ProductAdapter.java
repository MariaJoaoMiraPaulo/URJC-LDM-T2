package com.example.mariajoaomirapaulo.myshoppinglist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter{

    private Activity context;
    private ArrayList<String> list;

    public ProductAdapter(Activity context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
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

        name.setText(productItem);

        return convertView;
    }
}

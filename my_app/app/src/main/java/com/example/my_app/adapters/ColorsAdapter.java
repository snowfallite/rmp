package com.example.my_app.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ColorsAdapter extends ArrayAdapter<String> {
    private final int[] colors;

    public ColorsAdapter(Context context, String[] names, int[] colors) {
        super(context, android.R.layout.simple_list_item_1, names);
        this.colors = colors;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setBackgroundColor(colors[position]);
        return view;
    }
}

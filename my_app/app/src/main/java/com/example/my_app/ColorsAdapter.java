package com.example.my_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ColorsAdapter extends BaseAdapter {
    private final String[] colorNames;
    private final int[] colorValues;
    private final Context context;

    public ColorsAdapter(Context context, String[] colorNames, int[] colorValues) {
        this.context = context;
        this.colorNames = colorNames;
        this.colorValues = colorValues;
    }

    @Override
    public int getCount() {
        return colorNames.length;
    }

    @Override
    public Object getItem(int position) {
        return colorValues[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_color, parent, false);
        }

        TextView textView = view.findViewById(R.id.textColorName);
        textView.setText(colorNames[position]);
        textView.setBackgroundColor(colorValues[position]);

        return view;
    }
}

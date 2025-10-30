package com.example.my_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.my_app.R;

public class ColorsAdapter extends BaseAdapter {

    private final Context context;
    private final String[] colorNames;
    private final int[] colorValues;
    private final LayoutInflater inflater;

    public ColorsAdapter(Context context, String[] colorNames, int[] colorValues) {
        this.context = context;
        this.colorNames = colorNames;
        this.colorValues = colorValues;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return colorNames.length;
    }

    @Override
    public Object getItem(int position) {
        return colorNames[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_color, parent, false);
            holder = new ViewHolder();
            holder.nameTextView = convertView.findViewById(R.id.colorName);
            holder.colorView = convertView.findViewById(R.id.colorPreview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nameTextView.setText(colorNames[position]);
        holder.colorView.setBackgroundColor(colorValues[position]);

        return convertView;
    }

    static class ViewHolder {
        TextView nameTextView;
        View colorView;
    }
}

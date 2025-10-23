package com.example.my_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class ColorsAdapter extends BaseAdapter {
    private final String[] names;
    private final Context context;

    // Конструктор адаптера
    public ColorsAdapter(Context context, String[] names) {
        this.context = context;
        this.names = names;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return names[position];
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
            view = inflater.inflate(R.layout.list_item, parent, false);
        }


        TextView textView = view.findViewById(R.id.itemText);
        textView.setText(names[position]);

        if (position % 2 == 0) {
            textView.setBackgroundColor(ContextCompat.getColor(context, R.color.pink));
        } else {
            textView.setBackgroundColor(ContextCompat.getColor(context, R.color.light_blue));
        }

        return view;
    }
}

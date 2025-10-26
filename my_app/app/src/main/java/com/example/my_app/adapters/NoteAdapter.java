package com.example.my_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.my_app.R;
import com.example.my_app.activities.MainActivity;
import java.util.List;

public class NoteAdapter extends BaseAdapter {
    private Context context;
    private List<String> notes;
    private LayoutInflater inflater;

    public NoteAdapter(Context context) {
        this.context = context;
        // Получаем ссылку на MainActivity и список данных
        MainActivity mainActivity = (MainActivity) context;
        this.notes = mainActivity.getNotes();
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public String getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = inflater.inflate(R.layout.item_note, parent, false);
        } else {
            view = convertView;
        }

        // Получаем текст записи
        String noteText = notes.get(position);

        // Находим TextView и устанавливаем текст
        TextView textView = view.findViewById(R.id.textView);
        textView.setText(noteText);

        // Находим цветной квадратик и устанавливаем цвет
        View colorSquare = view.findViewById(R.id.colorSquare);
        int color = getColorForPosition(position);
        colorSquare.setBackgroundColor(color);

        return view;
    }

    private int getColorForPosition(int position) {
        // Генерируем разные цвета для разных позиций
        String[] colors = {
                "#4CAF50", "#2196F3", "#FF9800", "#F44336", "#9C27B0",
                "#00BCD4", "#FF5722", "#795548", "#607D8B", "#CDDC39"
        };
        int index = position % colors.length;
        return android.graphics.Color.parseColor(colors[index]);
    }
}

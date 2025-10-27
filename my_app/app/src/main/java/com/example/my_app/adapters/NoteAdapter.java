package com.example.my_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.my_app.R;
import com.example.my_app.activities.MainActivity;
import com.example.my_app.model.Note;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class NoteAdapter extends BaseAdapter {
    private Context context;
    private List<Note> notes;
    private LayoutInflater inflater;
    private SimpleDateFormat dateFormat;

    public NoteAdapter(Context context) {
        this.context = context;
        // Получаем ссылку на MainActivity и список данных
        MainActivity mainActivity = (MainActivity) context;
        this.notes = mainActivity.getNotes();
        this.inflater = LayoutInflater.from(context);
        this.dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Note getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_note, parent, false);
            holder = new ViewHolder();
            holder.colorSquare = convertView.findViewById(R.id.colorSquare);
            holder.textTitle = convertView.findViewById(R.id.textTitle);
            holder.textContent = convertView.findViewById(R.id.textContent);
            holder.textDateTime = convertView.findViewById(R.id.textDateTime);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Получаем объект Note
        Note note = notes.get(position);

        // Устанавливаем данные в TextView
        holder.textTitle.setText(note.getTitle());
        holder.textContent.setText(note.getContent());
        holder.textDateTime.setText(dateFormat.format(note.getDateTime()));

        // Устанавливаем цветной квадратик
        int color = getColorForPosition(position);
        holder.colorSquare.setBackgroundColor(color);

        return convertView;
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

    // ViewHolder pattern для оптимизации
    private static class ViewHolder {
        View colorSquare;
        TextView textTitle;
        TextView textContent;
        TextView textDateTime;
    }
}

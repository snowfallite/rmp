package com.example.my_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.example.my_app.R;
import com.example.my_app.adapters.NoteAdapter;
import com.example.my_app.base.MyBaseActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends MyBaseActivity {

    private List<String> notes;
    private ListView listView;
    private NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация списка заметок
        notes = new ArrayList<>();
        // Добавляем тестовые записи
        notes.add("Record 1");
        notes.add("Record 2");

        listView = findViewById(R.id.listView);
        Button addButton = findViewById(R.id.addButton);

        // Создаем и устанавливаем адаптер
        adapter = new NoteAdapter(this);
        listView.setAdapter(adapter);

        // Обработчик нажатия кнопки Add
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNoteActivityForCreate();
            }
        });

        // Обработчик нажатия на элемент списка
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openNoteActivityForEdit(position);
            }
        });
    }

    // Getter для доступа к списку из адаптера
    public List<String> getNotes() {
        return notes;
    }

    private void openNoteActivityForCreate() {
        Intent intent = new Intent(this, NoteActivity.class);
        startActivityForResult(intent, CREATE_ACTION);
    }

    private void openNoteActivityForEdit(int position) {
        Intent intent = new Intent(this, NoteActivity.class);
        // Передаем текст заметки и позицию
        intent.putExtra(EXTRA_TEXT, notes.get(position));
        intent.putExtra(EXTRA_ID, position);
        startActivityForResult(intent, EDIT_ACTION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                String text = extras.getString(EXTRA_TEXT);

                switch (requestCode) {
                    case CREATE_ACTION:
                        // Добавляем новую строку в список
                        if (text != null && !text.trim().isEmpty()) {
                            notes.add(text);
                            adapter.notifyDataSetChanged(); // Обновляем адаптер
                        }
                        break;
                    case EDIT_ACTION:
                        // Извлекаем позицию строки и редактируем
                        int position = extras.getInt(EXTRA_ID);
                        if (text != null && position >= 0 && position < notes.size()) {
                            notes.set(position, text);
                            adapter.notifyDataSetChanged(); // Обновляем адаптер
                        }
                        break;
                }
            }
        }
    }

    public void goBack(View view) {
        onBackPressed();
    }
}

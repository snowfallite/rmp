package com.example.my_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.example.my_app.R;
import com.example.my_app.base.MyBaseActivity;
import java.util.ArrayList;

public class MainActivity extends MyBaseActivity {

    private ArrayList<String> notesList;
    private ArrayAdapter<String> adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        Button addButton = findViewById(R.id.addButton);

        // Инициализация списка заметок
        notesList = new ArrayList<>();

        // Создание адаптера
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesList);
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

    private void openNoteActivityForCreate() {
        Intent intent = new Intent(this, NoteActivity.class);
        startActivityForResult(intent, CREATE_ACTION);
    }

    private void openNoteActivityForEdit(int position) {
        Intent intent = new Intent(this, NoteActivity.class);
        // Передаем текст заметки и позицию
        intent.putExtra(EXTRA_TEXT, notesList.get(position));
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
                            notesList.add(text);
                            adapter.notifyDataSetChanged();
                        }
                        break;
                    case EDIT_ACTION:
                        // Извлекаем позицию строки и редактируем
                        int position = extras.getInt(EXTRA_ID);
                        if (text != null && position >= 0 && position < notesList.size()) {
                            notesList.set(position, text);
                            adapter.notifyDataSetChanged();
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

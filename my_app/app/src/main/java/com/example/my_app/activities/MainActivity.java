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
import com.example.my_app.model.Note;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends MyBaseActivity {

    private List<Note> notes;
    private ListView listView;
    private NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация списка заметок
        notes = new ArrayList<>();
        // Добавляем тестовые записи
        notes.add(new Note("Заголовок 1", "Содержание первой записи", new Date()));
        notes.add(new Note("Заголовок 2", "Содержание второй записи", new Date()));

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
    public List<Note> getNotes() {
        return notes;
    }

    private void openNoteActivityForCreate() {
        Intent intent = new Intent(this, NoteActivity.class);
        startActivityForResult(intent, CREATE_ACTION);
    }

    private void openNoteActivityForEdit(int position) {
        Intent intent = new Intent(this, NoteActivity.class);
        // Передаем объект Note и позицию
        intent.putExtra(EXTRA_NOTE, notes.get(position));
        intent.putExtra(EXTRA_ID, position);
        startActivityForResult(intent, EDIT_ACTION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Note note = (Note) extras.getSerializable(EXTRA_NOTE);

                switch (requestCode) {
                    case CREATE_ACTION:
                        // Добавляем новую запись в список
                        if (note != null && note.getTitle() != null && !note.getTitle().trim().isEmpty()) {
                            notes.add(note);
                            adapter.notifyDataSetChanged(); // Обновляем адаптер
                        }
                        break;
                    case EDIT_ACTION:
                        // Извлекаем позицию и редактируем запись
                        int position = extras.getInt(EXTRA_ID);
                        if (note != null && position >= 0 && position < notes.size()) {
                            notes.set(position, note);
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

package com.example.my_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.my_app.R;
import com.example.my_app.base.MyBaseActivity;
import com.example.my_app.model.Note;
import java.util.Date;

public class NoteActivity extends MyBaseActivity {

    private EditText editTitle;
    private EditText editContent;
    private Button okButton;
    private Note originalNote;
    private int originalPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        editTitle = findViewById(R.id.editTitle);
        editContent = findViewById(R.id.editContent);
        okButton = findViewById(R.id.okButton);

        // Получаем Intent, с помощью которого была запущена активность
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Если передан объект Note, отображаем его данные
            originalNote = (Note) extras.getSerializable(EXTRA_NOTE);
            originalPosition = extras.getInt(EXTRA_ID, -1);

            if (originalNote != null) {
                editTitle.setText(originalNote.getTitle());
                editContent.setText(originalNote.getContent());
                editTitle.setSelection(originalNote.getTitle().length()); // Устанавливаем курсор в конец
            }
        }

        // Изначально делаем кнопку недоступной
        okButton.setEnabled(false);

        // Устанавливаем слушатель изменений текста для обоих EditText
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Проверяем, есть ли текст в заголовке (пробелы не считаются)
                String title = editTitle.getText().toString().trim();
                boolean hasTitle = title.length() > 0;

                // Устанавливаем доступность кнопки
                okButton.setEnabled(hasTitle);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        editTitle.addTextChangedListener(textWatcher);
        editContent.addTextChangedListener(textWatcher);
    }

    public void onOK(View view) {
        Intent intent = getIntent(); // Получаем Intent, с помощью которого была запущена активность

        // Создаем новый объект Note или обновляем существующий
        String title = editTitle.getText().toString();
        String content = editContent.getText().toString();
        Date time = (originalNote != null) ? originalNote.getTime() : new Date();

        // Если это новая запись, обновляем время создания
        if (originalNote == null) {
            time = new Date();
        }

        Note note = new Note(title, content, time);

        // Помещаем в Intent объект Note
        intent.putExtra(EXTRA_NOTE, note);

        // Если передавалась позиция, она остается в Intent
        if (originalPosition != -1) {
            intent.putExtra(EXTRA_ID, originalPosition);
        }

        // Устанавливаем результат и завершаем активность
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onCancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void goBack(View view) {
        onBackPressed();
    }
}

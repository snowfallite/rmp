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

public class NoteActivity extends MyBaseActivity {

    private EditText editText;
    private Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        editText = findViewById(R.id.editText);
        okButton = findViewById(R.id.okButton);

        // Устанавливаем многострочный режим
        editText.setSingleLine(false);
        editText.setLines(5);

        // Получаем Intent, с помощью которого была запущена активность
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Если передан текст, отображаем его в EditText
            String text = extras.getString(EXTRA_TEXT);
            if (text != null) {
                editText.setText(text);
                editText.setSelection(text.length()); // Устанавливаем курсор в конец
            }
        }

        // Изначально делаем кнопку недоступной
        okButton.setEnabled(false);

        // Устанавливаем слушатель изменений текста
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Можно оставить пустым
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Проверяем, есть ли текст (пробелы не считаются)
                String text = s.toString().trim();
                boolean hasText = text.length() > 0;

                // Устанавливаем доступность кнопки
                okButton.setEnabled(hasText);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Можно оставить пустым
            }
        });
    }

    public void onOK(View view) {
        Intent intent = getIntent(); // Получаем Intent, с помощью которого была запущена активность

        // Помещаем в Intent текст из EditText
        String text = editText.getText().toString();
        intent.putExtra(EXTRA_TEXT, text);

        // Если передавалась позиция, она остается в Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int id = extras.getInt(EXTRA_ID, -1);
            if (id != -1) {
                intent.putExtra(EXTRA_ID, id);
            }
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

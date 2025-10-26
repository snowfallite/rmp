package com.example.my_app.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.my_app.R;
import com.example.my_app.base.MyBaseActivity;

public class MainActivity extends MyBaseActivity implements TextWatcher {

    private EditText editText;
    private Button copyButton;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Находим все элементы
        editText = findViewById(R.id.editText);
        copyButton = findViewById(R.id.copyButton);
        textView = findViewById(R.id.textView);

        // Устанавливаем слушатель изменений текста
        editText.addTextChangedListener(this);

        // Изначально делаем кнопку недоступной
        copyButton.setEnabled(false);

        // Устанавливаем обработчик нажатия на кнопку
        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyText();
            }
        });
    }

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
        copyButton.setEnabled(hasText);
    }

    @Override
    public void afterTextChanged(Editable s) {
        // Можно оставить пустым
    }

    private void copyText() {
        // Копируем содержимое EditText в TextView
        String text = editText.getText().toString();
        textView.setText(text);
    }

    public void goBack(View view) {
        onBackPressed();
    }
}

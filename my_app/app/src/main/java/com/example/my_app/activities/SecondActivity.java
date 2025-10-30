package com.example.my_app.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.my_app.R;
import com.example.my_app.base.MyApplication;

public class SecondActivity extends AppCompatActivity {

    private EditText editText;
    private Button okButton;
    private MyApplication myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Получаем ссылку на объект-приложение
        myApp = (MyApplication) getApplicationContext();

        editText = findViewById(R.id.editText);
        okButton = findViewById(R.id.okButton);

        // Восстанавливаем значение из MyApplication
        restoreDataFromApp();

        // Изначально кнопка неактивна
        okButton.setEnabled(!editText.getText().toString().isEmpty());

        // Слушатель для изменения текста в поле ввода
        editText.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Включаем кнопку, если поле не пустое
                boolean isEnabled = charSequence.length() > 0;
                // Дополнительная проверка на число
                if (isEnabled) {
                    try {
                        Double.parseDouble(charSequence.toString());
                        okButton.setEnabled(true);
                    } catch (NumberFormatException e) {
                        okButton.setEnabled(false);
                    }
                } else {
                    okButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(android.text.Editable editable) {}
        });

        // Обработка нажатия кнопки OK
        okButton.setOnClickListener(v -> {
            String inputText = editText.getText().toString().trim();

            if (!inputText.isEmpty()) {
                try {
                    // Проверяем, что введено число
                    Double.parseDouble(inputText);

                    // Сохраняем данные в MyApplication
                    myApp.setResultData(inputText);

                    // Закрываем активность
                    finish();

                } catch (NumberFormatException e) {
                    // Если не число, кнопка и так будет disabled
                }
            }
        });
    }

    // Восстанавливаем данные из MyApplication в EditText
    private void restoreDataFromApp() {
        String savedValue = myApp.getResultData();
        if (savedValue != null && !savedValue.isEmpty()) {
            editText.setText(savedValue);
            editText.setSelection(savedValue.length()); // Курсор в конец
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        // При нажатии кнопки "Назад" сохраняем текущее значение
        saveCurrentData();
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        // При системной кнопке "Назад" сохраняем текущее значение
        saveCurrentData();
        super.onBackPressed();
    }

    // Сохраняем текущие данные из EditText в MyApplication
    private void saveCurrentData() {
        String currentText = editText.getText().toString().trim();
        if (!currentText.isEmpty()) {
            try {
                Double.parseDouble(currentText);
                myApp.setResultData(currentText);
            } catch (NumberFormatException e) {
                // Игнорируем нечисловые значения
            }
        }
    }
}

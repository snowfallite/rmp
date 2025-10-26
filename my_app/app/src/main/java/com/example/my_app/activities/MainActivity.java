package com.example.my_app.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.my_app.R;
import com.example.my_app.base.MyBaseActivity;

public class MainActivity extends MyBaseActivity {

    private EditText editTextSimple;
    private EditText editTextName;
    private EditText editTextAge;
    private LinearLayout containerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация элементов
        editTextSimple = findViewById(R.id.editTextSimple);
        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        containerLayout = findViewById(R.id.containerLayout);

        Button buttonAddText = findViewById(R.id.buttonAddText);
        Button buttonAddPerson = findViewById(R.id.buttonAddPerson);

        // Обработчики кнопок
        buttonAddText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSimpleText();
            }
        });

        buttonAddPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPersonInfo();
            }
        });
    }

    private void addSimpleText() {
        String text = editTextSimple.getText().toString().trim();
        if (!text.isEmpty()) {
            // Создаем LayoutInflater
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // Создаем TextView из разметки
            TextView textView = (TextView) inflater.inflate(R.layout.item_text, null);

            // Устанавливаем текст
            textView.setText(text);

            // Добавляем в контейнер
            containerLayout.addView(textView);

            // Очищаем EditText
            editTextSimple.setText("");
        }
    }

    private void addPersonInfo() {
        String name = editTextName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();

        if (!name.isEmpty() && !age.isEmpty()) {
            // Создаем LayoutInflater
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // Создаем сложный элемент из разметки
            LinearLayout root = (LinearLayout) inflater.inflate(R.layout.item_person, null);

            // Находим TextView для имени и устанавливаем текст
            TextView textName = (TextView) root.findViewById(R.id.textName);
            textName.setText("Name: " + name);

            // Находим TextView для возраста и устанавливаем текст
            TextView textAge = (TextView) root.findViewById(R.id.textAge);
            textAge.setText("Age: " + age);

            // Добавляем в контейнер
            containerLayout.addView(root);

            // Очищаем EditText-ы
            editTextName.setText("");
            editTextAge.setText("");
        }
    }

    public void goBack(View view) {
        onBackPressed();
    }
}

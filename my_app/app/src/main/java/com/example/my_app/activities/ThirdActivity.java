package com.example.my_app.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.my_app.R;

public class ThirdActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textView1, textView2;
    Button buttonAdd, buttonCopy, buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third); // Указываем разметку для этой активности

        // Инициализация элементов UI
        textView1 = findViewById(R.id.tv1);
        textView2 = findViewById(R.id.tv2);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonCopy = findViewById(R.id.buttonCopy);
        buttonBack = findViewById(R.id.buttonBack);

        // Устанавливаем обработчики нажатий
        buttonAdd.setOnClickListener(this);
        buttonCopy.setOnClickListener(this);
        buttonBack.setOnClickListener(this);

        // Применяем цвет фона из ресурсов
        int backgroundColor = getResources().getColor(R.color.bg); // bg — это ваш ресурс цвета
        findViewById(android.R.id.content).setBackgroundColor(backgroundColor); // Устанавливаем цвет фона на root view
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        // Обработка нажатия на кнопку "Add *"
        if (id == R.id.buttonAdd) {
            String s = textView1.getText().toString();
            textView1.setText(s + "*");
        }
        // Обработка нажатия на кнопку "Copy"
        else if (id == R.id.buttonCopy) {
            textView2.setText(textView1.getText().toString());
        }
        // Обработка нажатия на кнопку "Back"
        else if (id == R.id.buttonBack) {
            finish(); // Закрываем активность
        }
    }
}

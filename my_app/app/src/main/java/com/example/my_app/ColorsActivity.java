package com.example.my_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

public class ColorsActivity extends AppCompatActivity {

    private static final String PREFS = "MyPrefs";
    private static final String KEY_COLOR = "bg_color";

    private int[] colorValues;
    private String[] colorNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);

        ListView listView = findViewById(R.id.listViewColors);
        Button btnBack = findViewById(R.id.btnBack);

        // восстанавливаем массивы
        colorNames = getResources().getStringArray(R.array.color_names);
        colorValues = getResources().getIntArray(R.array.color_values);

        // создаём адаптер и связываем со списком
        ColorsAdapter adapter = new ColorsAdapter(this, colorNames, colorValues);
        listView.setAdapter(adapter);

        // обработчик выбора цвета
        listView.setOnItemClickListener((AdapterView<?> parent, android.view.View view, int position, long id) -> {
            int selectedColor = colorValues[position];

            // сохраняем цвет
            SharedPreferences.Editor editor = getSharedPreferences(PREFS, MODE_PRIVATE).edit();
            editor.putInt(KEY_COLOR, selectedColor);
            editor.apply();

            // сразу перекрашиваем окно текущей активности
            getWindow().getDecorView().setBackgroundColor(selectedColor);
        });

        // кнопка Back
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(ColorsActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        int color = prefs.getInt(KEY_COLOR, 0xFFFFFFFF);
        getWindow().getDecorView().setBackgroundColor(color);
    }
}

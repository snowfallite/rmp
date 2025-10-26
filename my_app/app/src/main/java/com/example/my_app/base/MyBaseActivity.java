package com.example.my_app.base;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MyBaseActivity extends AppCompatActivity {

    protected SharedPreferences pref; // общие настройки

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getSharedPreferences("MySettings", MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Восстанавливаем сохранённый цвет
        int color = pref.getInt("color", 0xFFFFFFFF); // по умолчанию — белый

        // Красим окно (а не layout!)
        getWindow().getDecorView().setBackgroundColor(color);
    }
}

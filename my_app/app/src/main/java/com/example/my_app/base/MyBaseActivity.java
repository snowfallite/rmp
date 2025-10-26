package com.example.my_app.base;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MyBaseActivity extends AppCompatActivity {

    protected SharedPreferences pref; // общие настройки
    protected static final int CREATE_ACTION = 0x000312;
    protected static final int EDIT_ACTION = 0x000313;
    protected static final String EXTRA_TEXT = "text";
    protected static final String EXTRA_ID = "id";
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

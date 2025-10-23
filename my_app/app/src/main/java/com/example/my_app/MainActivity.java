package com.example.my_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS = "MyPrefs";
    private static final String KEY_COLOR = "bg_color";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnChoose = findViewById(R.id.btnChooseColor);
        btnChoose.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ColorsActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // восстановление выбранного цвета
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        int color = prefs.getInt(KEY_COLOR, 0xFFFFFFFF); // белый по умолчанию
        getWindow().getDecorView().setBackgroundColor(color);
    }
}

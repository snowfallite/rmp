package com.example.my_app.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import com.example.my_app.R;
import com.example.my_app.base.MyBaseActivity;

public class ThirdActivity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Button btnRed = findViewById(R.id.btnRed);
        Button btnGreen = findViewById(R.id.btnGreen);
        Button btnBlue = findViewById(R.id.btnBlue);
        Button btnBack = findViewById(R.id.btnBack);

        btnRed.setOnClickListener(v -> saveColor(0xFFFFCCCC));
        btnGreen.setOnClickListener(v -> saveColor(0xFFCCFFCC));
        btnBlue.setOnClickListener(v -> saveColor(0xFFCCCCFF));

        btnBack.setOnClickListener(v -> finish());
    }

    private void saveColor(int color) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("color", color);
        editor.apply();
        getWindow().getDecorView().setBackgroundColor(color);
    }
}

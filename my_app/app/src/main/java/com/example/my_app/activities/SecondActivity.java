package com.example.my_app.activities;

import android.os.Bundle;
import android.widget.Button;
import com.example.my_app.R;
import com.example.my_app.base.MyBaseActivity;

public class SecondActivity extends MyBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }
}

package com.example.my_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.my_app.R;
import com.example.my_app.base.MyBaseActivity;

public class MainActivity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnChangeColor = findViewById(R.id.btnChooseColor);
        btnChangeColor.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ColorsActivity.class));
        });

        Button btnThirdActivity = findViewById(R.id.btnThirdActivity);
        btnThirdActivity.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ThirdActivity.class));
        });
    }

    public void goBack(View view) {
        onBackPressed();
    }
}

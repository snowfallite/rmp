package com.example.my_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.my_app.R;
import com.example.my_app.base.MyBaseActivity;

public class MainActivity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnFirst = findViewById(R.id.btnFirst);
        Button btnSecond = findViewById(R.id.btnSecond);
        Button btnThird = findViewById(R.id.btnThird);

        btnFirst.setOnClickListener(v ->
                startActivity(new Intent(this, FirstActivity.class)));

        btnSecond.setOnClickListener(v ->
                startActivity(new Intent(this, SecondActivity.class)));

        btnThird.setOnClickListener(v ->
                startActivity(new Intent(this, ThirdActivity.class)));
    }
}

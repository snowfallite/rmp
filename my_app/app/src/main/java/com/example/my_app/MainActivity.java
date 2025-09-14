package com.example.my_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity
implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = (Button)findViewById(R.id.button7);
        Button btn2 = (Button)findViewById(R.id.button8);
        Button btn3 = (Button)findViewById(R.id.button9);
        Button btn4 = (Button)findViewById(R.id.button10);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        Button btn = (Button)v;

        btn.setText(R.string.AfterClick);

    }
}
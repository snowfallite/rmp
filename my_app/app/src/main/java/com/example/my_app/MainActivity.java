package com.example.my_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textView1, textView2;
    Button buttonAdd, buttonCopy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView1 = findViewById(R.id.tv1);
        textView2 = findViewById(R.id.tv2);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonCopy = findViewById(R.id.buttonCopy);


        buttonAdd.setOnClickListener(this);
        buttonCopy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.buttonAdd) {

            String s = textView1.getText().toString();
            textView1.setText(s + "*");
        }
        else if (id == R.id.buttonCopy) {

            textView2.setText(textView1.getText().toString());
        }
    }
}

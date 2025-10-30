package com.example.my_app.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.my_app.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_locations).setOnClickListener(v ->
                startActivity(new Intent(this, FirstActivity.class)));

        findViewById(R.id.btn_rates).setOnClickListener(v ->
                startActivity(new Intent(this, SecondActivity.class)));

        findViewById(R.id.btn_login).setOnClickListener(v -> showLoginDialog());
    }

    private void showLoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Вход");
        View view = getLayoutInflater().inflate(R.layout.dialog_login, null);
        builder.setView(view);
        builder.setPositiveButton("OK", (d, w) -> {});
        builder.setNegativeButton("Отмена", (d, w) -> d.dismiss());
        builder.show();
    }
}

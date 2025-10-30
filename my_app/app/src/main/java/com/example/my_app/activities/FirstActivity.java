package com.example.my_app.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.my_app.R;
import com.example.my_app.base.MyBaseActivity;

import java.util.ArrayList;

public class FirstActivity extends MyBaseActivity {

    private ArrayList<String> list = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        EditText editTextWord = findViewById(R.id.editTextWord);
        EditText editTextCount = findViewById(R.id.editTextCount);
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnClear = findViewById(R.id.btnClear);
        Button btnBack = findViewById(R.id.btnBack);
        ListView listView = findViewById(R.id.listView);


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> {
            String word = editTextWord.getText().toString();
            String countStr = editTextCount.getText().toString();

            if (!word.isEmpty() && !countStr.isEmpty()) {
                int count = Integer.parseInt(countStr);
                StringBuilder result = new StringBuilder();
                for (int i = 0; i < count; i++) {
                    result.append(word);
                }
                list.add(result.toString());
                adapter.notifyDataSetChanged();
            }
        });

        btnClear.setOnClickListener(v -> {
            list.clear();
            adapter.notifyDataSetChanged();
        });

        btnBack.setOnClickListener(v -> finish());
    }
}

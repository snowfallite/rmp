package com.example.my_app.activities;
import com.example.my_app.R;
import android.os.Bundle;
import android.widget.*;
import com.example.my_app.base.MyBaseActivity;
import java.util.ArrayList;

public class MainActivity extends MyBaseActivity {

    private ArrayList<String> listData;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextWord = findViewById(R.id.editTextWord);
        EditText editTextCount = findViewById(R.id.editTextCount);
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnClear = findViewById(R.id.btnClear);
        ListView listView = findViewById(R.id.listView);

        listData = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> {
            String word = editTextWord.getText().toString().trim();
            String countStr = editTextCount.getText().toString().trim();

            if (word.isEmpty() || countStr.isEmpty()) {
                Toast.makeText(this, "Введите строку и количество!", Toast.LENGTH_SHORT).show();
                return;
            }

            int count;
            try {
                count = Integer.parseInt(countStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Количество должно быть числом!", Toast.LENGTH_SHORT).show();
                return;
            }

            StringBuilder result = new StringBuilder();
            for (int i = 0; i < count; i++) {
                result.append(word);
            }

            listData.add(result.toString());
            adapter.notifyDataSetChanged();

            editTextWord.setText("");
            editTextCount.setText("");
        });

        btnClear.setOnClickListener(v -> {
            listData.clear();
            adapter.notifyDataSetChanged();
        });
    }
}

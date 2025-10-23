package com.example.my_app;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class SecondActivity extends AppCompatActivity {
    private ArrayAdapter<String> adapter1;
    private ArrayAdapter<String> adapter2;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ListView listView1 = findViewById(R.id.listView1);
        ListView listView2 = findViewById(R.id.listView2);
        editText = findViewById(R.id.editText);


        String[] ar1 = getResources().getStringArray(R.array.ar1);
        String[] ar2 = getResources().getStringArray(R.array.ar2);

        adapter1 = new ArrayAdapter<>(this, R.layout.list_item);
        adapter2 = new ArrayAdapter<>(this, R.layout.list_item);

        adapter1.addAll(Arrays.asList(ar1));
        adapter2.addAll(Arrays.asList(ar2));

        listView1.setAdapter(adapter1);
        listView2.setAdapter(adapter2);
    }

    public void addToList1(View view) {
        String text = editText.getText().toString().trim();
        if (!text.isEmpty()) {
            adapter1.add(text);
            editText.setText("");
        }
    }

    public void addToList2(View view) {
        String text = editText.getText().toString().trim();
        if (!text.isEmpty()) {
            adapter2.add(text);
            editText.setText("");
        }
    }

    public void goBack(View view) {
        onBackPressed();
    }
}

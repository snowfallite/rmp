package com.example.my_app;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // получаем ссылку на ListView
        ListView listView = findViewById(R.id.listView);

        // загружаем массив из ресурсов
        String[] items = getResources().getStringArray(R.array.items_array);

        // создаём и устанавливаем адаптер
        ColorsAdapter adapter = new ColorsAdapter(this, items);
        listView.setAdapter(adapter);
    }
}

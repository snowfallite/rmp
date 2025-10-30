package com.example.my_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.my_app.R;
import com.example.my_app.base.MyApplication;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private Button addButton, deleteButton;
    private ArrayList<String> expressions;
    private ArrayAdapter<String> adapter;
    private MyApplication myApp;
    private int selectedPosition = ListView.INVALID_POSITION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Получаем ссылку на объект-приложение
        myApp = (MyApplication) getApplicationContext();

        listView = findViewById(R.id.listView);
        addButton = findViewById(R.id.addButton);
        deleteButton = findViewById(R.id.deleteButton);

        expressions = new ArrayList<>();

        // Создаем кастомный адаптер с голубым выделением
        adapter = new ArrayAdapter<String>(this, R.layout.list_item_blue, R.id.textViewItem, expressions) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = view.findViewById(R.id.textViewItem);

                // Устанавливаем выделение голубым цветом
                if (position == selectedPosition) {
                    view.setBackgroundColor(getResources().getColor(R.color.blue_light));
                } else {
                    view.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                }

                return view;
            }
        };

        listView.setAdapter(adapter);

        // Настраиваем выбор элемента в списке
        setupListViewSelection();

        // Восстанавливаем последнее значение из MyApplication и добавляем в список
        restoreDataFromApp();

        // Открытие SecondActivity
        addButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SecondActivity.class));
        });

        // Обработчик нажатия на кнопку Delete
        deleteButton.setOnClickListener(v -> {
            deleteSelectedItem();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        restoreDataFromApp();
    }

    // Настройка выбора элемента в ListView
    private void setupListViewSelection() {
        // Обработчик клика по элементу списка
        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            adapter.notifyDataSetChanged(); // Обновляем все элементы для перерисовки фона
        });

        // Обработчик длинного нажатия
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            adapter.notifyDataSetChanged();
            return true;
        });
    }

    // Удаление выбранного элемента
    private void deleteSelectedItem() {
        if (selectedPosition != ListView.INVALID_POSITION && selectedPosition < expressions.size()) {
            expressions.remove(selectedPosition);
            adapter.notifyDataSetChanged();

            // Сбрасываем выделение после удаления
            selectedPosition = ListView.INVALID_POSITION;
        }
    }

    // Восстанавливаем данные из MyApplication и добавляем в список
    private void restoreDataFromApp() {
        String xValue = myApp.getResultData();
        if (xValue != null && !xValue.isEmpty()) {
            try {
                double x = Double.parseDouble(xValue);
                double result = x * x + 4 * x;
                String expression = String.format("f(%.0f) = %.0f^2 + 4*%.0f = %.0f", x, x, x, result);

                if (!expressions.contains(expression)) {
                    expressions.add(expression);
                    adapter.notifyDataSetChanged();

                    // Автоматически выделяем последний добавленный элемент голубым
                    if (expressions.size() > 0) {
                        selectedPosition = expressions.size() - 1;
                        adapter.notifyDataSetChanged();
                        listView.smoothScrollToPosition(selectedPosition);
                    }
                }
            } catch (NumberFormatException e) {
                // Игнорируем нечисловые значения
            }
        }
    }
}

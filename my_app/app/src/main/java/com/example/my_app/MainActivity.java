package com.example.my_app;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ArrayAdapter<String> arad;
    private ListView lw;
    private EditText et;
    private Button btnAdd, btnEdit, btnDel, btnClear;
    private int curPos = -1; // позиция выбранного элемента
    private View curView = null; // ссылка на выделенный элемент

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lw = findViewById(R.id.lw);
        et = findViewById(R.id.et);
        btnAdd = findViewById(R.id.btnAdd);
        btnEdit = findViewById(R.id.btnEdit);
        btnDel = findViewById(R.id.btnDel);
        btnClear = findViewById(R.id.btnClear);

        // создаем адаптер для списка
        arad = new ArrayAdapter<>(this, R.layout.list_item);
        lw.setAdapter(arad);

        // добавляем начальные элементы
        arad.add("first");
        arad.add("second");
        arad.add("third");

        // обработчик щелчка по элементу списка
        lw.setOnItemClickListener(this);

        // блокируем кнопки Edit и Del
        btnEdit.setEnabled(false);
        btnDel.setEnabled(false);

        // Обработчик кнопки Add
        btnAdd.setOnClickListener(v -> {
            String s = et.getText().toString();
            if (!s.isEmpty()) {
                arad.add(s);
                et.setText("");
            }
        });

        // Обработчик кнопки Clear
        btnClear.setOnClickListener(v -> {
            arad.clear();
            et.setText("");
            curView = null;
            curPos = -1;
            btnEdit.setEnabled(false);
            btnDel.setEnabled(false);
        });

        // Обработчик кнопки Edit
        btnEdit.setOnClickListener(v -> {
            if (curPos >= 0) {
                String newText = et.getText().toString();
                String oldItem = arad.getItem(curPos);
                arad.remove(oldItem);
                arad.insert(newText, curPos);
            }
        });

        // Обработчик кнопки Del
        btnDel.setOnClickListener(v -> {
            if (curPos >= 0) {
                arad.remove(arad.getItem(curPos));
                curView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
                curView = null;
                curPos = -1;
                btnEdit.setEnabled(false);
                btnDel.setEnabled(false);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // перекрашиваем предыдущий выбранный элемент, если есть
        if (curView != null) {
            curView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
        }

        // выделяем новый
        view.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_red_light));

        // сохраняем ссылку и позицию
        curView = view;
        curPos = position;

        // копируем текст в EditText
        et.setText(arad.getItem(position));

        // разблокируем кнопки Edit и Del
        btnEdit.setEnabled(true);
        btnDel.setEnabled(true);
    }
}

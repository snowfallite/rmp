package com.example.my_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.example.my_app.R;
import com.example.my_app.base.MyBaseActivity;

public class SecondActivity extends MyBaseActivity {

    private EditText editText1;
    private EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);

        // Получаем Intent, с помощью которого была запущена активность
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Проверяем, были ли переданы данные
            String text1 = extras.getString(getString(R.string.key_text1));
            String text2 = extras.getString(getString(R.string.key_text2));

            // Помещаем извлеченные строки в EditText
            if (text1 != null) {
                editText1.setText(text1);
            }
            if (text2 != null) {
                editText2.setText(text2);
            }
        }
    }

    // Кнопка OK
    public void ready(View view) {
        Intent intent = getIntent(); // Получаем Intent, с помощью которого была запущена активность

        // Помещаем в объект класса Bundle данные из EditText
        intent.putExtra(getString(R.string.key_text1), editText1.getText().toString());
        intent.putExtra(getString(R.string.key_text2), editText2.getText().toString());

        // Устанавливаем результат и завершаем активность
        setResult(RESULT_OK, intent);
        finish();
    }

    // Кнопка Cancel
    public void onCancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}

package com.example.my_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.my_app.R;
import com.example.my_app.base.MyBaseActivity;

public class MainActivity extends MyBaseActivity {

    private EditText editText1;
    private EditText editText2;
    private Button buttonEdit;

    // Идентификатор типа запроса
    protected static final int MY_ACTION = 0x000314;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        buttonEdit = findViewById(R.id.buttonEdit);

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSecondActivity();
            }
        });
    }

    private void openSecondActivity() {
        Intent intent = new Intent(this, SecondActivity.class);

        // Передаем содержимое EditText в Intent
        intent.putExtra(getString(R.string.key_text1), editText1.getText().toString());
        intent.putExtra(getString(R.string.key_text2), editText2.getText().toString());

        // Запускаем активность для получения результата
        startActivityForResult(intent, MY_ACTION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Проверяем тип запроса (у нас только один)
        if (requestCode == MY_ACTION) {
            // Проверяем, был ли результат успешным
            if (resultCode == RESULT_OK) {
                // Получаем данные из Intent
                Bundle extras = data.getExtras();
                if (extras != null) {
                    // Извлекаем данные и копируем в EditText
                    String text1 = extras.getString(getString(R.string.key_text1));
                    String text2 = extras.getString(getString(R.string.key_text2));

                    if (text1 != null) {
                        editText1.setText(text1);
                    }
                    if (text2 != null) {
                        editText2.setText(text2);
                    }
                }
            }
            // Если resultCode == RESULT_CANCELED, ничего не делаем
        }
    }

    public void goBack(View view) {
        onBackPressed();
    }
}

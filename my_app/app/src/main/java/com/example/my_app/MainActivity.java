package com.example.my_app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    private EditText etStr1, etStr2, etInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etStr1 = findViewById(R.id.etStr1);
        etStr2 = findViewById(R.id.etStr2);
        etInt = findViewById(R.id.etInt);


        preferences = getSharedPreferences(
                getString(R.string.preferences),
                MODE_PRIVATE
        );
    }

    @Override
    protected void onResume() {
        super.onResume();


        String keyStr1 = getString(R.string.string_element_1);
        String keyStr2 = getString(R.string.string_element_2);
        String keyInt = getString(R.string.int_element);

        String str1 = preferences.getString(keyStr1, "");
        String str2 = preferences.getString(keyStr2, "");
        int intValue = preferences.getInt(keyInt, 0);

        etStr1.setText(str1);
        etStr2.setText(str2);
        etInt.setText(String.valueOf(intValue));
    }

    @Override
    protected void onPause() {
        super.onPause();


        String str1 = etStr1.getText().toString();
        String str2 = etStr2.getText().toString();
        int intValue = 0;
        try {
            intValue = Integer.parseInt(etInt.getText().toString());
        } catch (NumberFormatException ignored) { }


        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(getString(R.string.string_element_1), str1);
        editor.putString(getString(R.string.string_element_2), str2);
        editor.putInt(getString(R.string.int_element), intValue);
        editor.apply();
    }
}

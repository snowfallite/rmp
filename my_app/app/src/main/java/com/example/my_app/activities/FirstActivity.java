package com.example.my_app.activities;

import android.os.Bundle;
import com.example.my_app.R;
import com.example.my_app.base.MyBaseActivity;

public class FirstActivity extends MyBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        // Здесь будет код для задачи п.1
    }
}

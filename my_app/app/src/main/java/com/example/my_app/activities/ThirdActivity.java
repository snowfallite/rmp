package com.example.my_app.activities;

import android.os.Bundle;
import android.view.View;
import com.example.my_app.R;
import com.example.my_app.base.MyBaseActivity;

public class ThirdActivity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
    }

    public void goBack(View view) {
        onBackPressed();
    }
}

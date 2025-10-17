package com.example.my_app;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private TextView textView1, textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = findViewById(R.id.mLL);
        textView1 = findViewById(R.id.tv1);
        textView2 = findViewById(R.id.tv2);
    }

    // ---------- длинные кнопки ----------
    public void toRedGreen(View view) {
        int bgColor = ContextCompat.getColor(this, android.R.color.holo_red_dark);
        int textColor = ContextCompat.getColor(this, android.R.color.holo_green_light);
        textView1.setBackgroundColor(bgColor);
        textView2.setBackgroundColor(bgColor);
        textView1.setTextColor(textColor);
        textView2.setTextColor(textColor);
    }

    public void toYellowBlue(View view) {
        int bgColor = ContextCompat.getColor(this, android.R.color.holo_orange_light);
        int textColor = ContextCompat.getColor(this, android.R.color.holo_blue_light);
        textView1.setBackgroundColor(bgColor);
        textView2.setBackgroundColor(bgColor);
        textView1.setTextColor(textColor);
        textView2.setTextColor(textColor);
    }

    public void toBlackWhite(View view) {
        int bgColor = ContextCompat.getColor(this, android.R.color.black);
        int textColor = ContextCompat.getColor(this, android.R.color.white);
        textView1.setBackgroundColor(bgColor);
        textView2.setBackgroundColor(bgColor);
        textView1.setTextColor(textColor);
        textView2.setTextColor(textColor);
    }

    // ---------- короткие кнопки ----------
    public void setBgRed(View view) {
        linearLayout.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_red_light));
    }

    public void setBgBlue(View view) {
        linearLayout.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_blue_light));
    }

    public void setBgBlack(View view) {
        linearLayout.setBackgroundColor(ContextCompat.getColor(this, android.R.color.black));
    }
}

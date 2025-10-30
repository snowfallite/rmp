package com.example.my_app.activities;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.example.my_app.R;
import com.example.my_app.base.MyBaseActivity;
import com.example.my_app.adapters.ColorsAdapter;
public class ColorsActivity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);

        ListView listView = findViewById(R.id.listView);

        // Получаем массивы из ресурсов
        String[] names = getResources().getStringArray(R.array.color_names);
        int[] values = getResources().getIntArray(R.array.color_values);

        // Свой адаптер для списка цветов
        ColorsAdapter adapter = new ColorsAdapter(this, names, values);
        listView.setAdapter(adapter);

        // Обработка выбора элемента
        listView.setOnItemClickListener((parent, view, position, id) -> {
            int color = values[position];

            // Сохраняем выбранный цвет в SharedPreferences
            android.content.SharedPreferences.Editor editor = pref.edit();
            editor.putInt("color", color);
            editor.apply();

            // Перекрашиваем окно сразу
            getWindow().getDecorView().setBackgroundColor(color);
        });
    }

    public void goBack(View view) {
        onBackPressed();
    }
}

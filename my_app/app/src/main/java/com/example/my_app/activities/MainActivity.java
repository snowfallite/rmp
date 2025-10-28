package com.example.my_app.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.my_app.R;
import com.example.my_app.base.MyBaseActivity;
import com.example.my_app.data.AppDatabase;
import com.example.my_app.data.Person;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends MyBaseActivity {

    private EditText editLastName, editFirstName, editAge;
    private Button buttonAdd, buttonShow;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private AppDatabase db;
    private List<String> peopleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editLastName = findViewById(R.id.editLastName);
        editFirstName = findViewById(R.id.editFirstName);
        editAge = findViewById(R.id.editAge);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonShow = findViewById(R.id.buttonShow);
        listView = findViewById(R.id.listView);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, peopleList);
        listView.setAdapter(adapter);

        db = AppDatabase.getInstance(this);

        buttonAdd.setOnClickListener(view -> {
            String lastName = editLastName.getText().toString().trim();
            String firstName = editFirstName.getText().toString().trim();
            String ageText = editAge.getText().toString().trim();

            if (lastName.isEmpty() || firstName.isEmpty() || ageText.isEmpty()) {
                Toast.makeText(MainActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }

            int age = Integer.parseInt(ageText);
            db.personDao().insert(new Person(lastName, firstName, age));

            Toast.makeText(MainActivity.this, "Добавлено!", Toast.LENGTH_SHORT).show();

            editLastName.setText("");
            editFirstName.setText("");
            editAge.setText("");
        });

        buttonShow.setOnClickListener(view -> {
            List<Person> people = db.personDao().getAll();
            peopleList.clear();
            for (Person p : people) {
                peopleList.add(p.id + ". " + p.lastName + " " + p.firstName + " — " + p.age + " лет");
            }
            adapter.notifyDataSetChanged();
        });
    }
}

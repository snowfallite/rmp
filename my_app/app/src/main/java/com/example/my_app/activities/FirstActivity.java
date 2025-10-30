package com.example.my_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_app.R;
import com.example.my_app.adapters.LocationAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FirstActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LocationAdapter adapter;
    private List<LocationItem> locations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        recyclerView = findViewById(R.id.recycler_locations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new LocationAdapter(locations);
        recyclerView.setAdapter(adapter);

        new LoadLocationsTask().execute("http://192.168.0.103:5000/locations"); // заменить на свой URL
    }

    private class LoadLocationsTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return NetworkUtils.getJSON(urls[0]);
        }

        @Override
        protected void onPostExecute(String json) {
            if (json == null) {
                Toast.makeText(FirstActivity.this, "Ошибка загрузки", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONArray arr = new JSONArray(json);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject o = arr.getJSONObject(i);
                    locations.add(new LocationItem(
                            o.getString("city"),
                            o.getString("street"),
                            o.getString("house"),
                            o.getString("type"),
                            o.getString("working_hours")
                    ));
                }
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

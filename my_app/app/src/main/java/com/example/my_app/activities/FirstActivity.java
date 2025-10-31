package com.example.my_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.my_app.R;
import com.example.my_app.adapters.LocationAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FirstActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LocationAdapter adapter;
    private List<LocationItem> locations = new ArrayList<>();
    private static final String TAG = "FirstActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        recyclerView = findViewById(R.id.recycler_locations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new LocationAdapter(locations);
        recyclerView.setAdapter(adapter);

        if (isNetworkAvailable()) {
            loadLocations("http://192.168.0.103:5000/locations");
        } else {
            Toast.makeText(this, "Проверьте подключение к интернету", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    private void loadLocations(String url) {
        new Thread(() -> {
            Log.d(TAG, "Загрузка локаций с " + url);
            String json = NetworkUtils.getJsonString(url);  // Используем обновлённый метод
            runOnUiThread(() -> onPostExecute(json));
        }).start();
    }

    private void onPostExecute(String json) {
        if (json == null || json.isEmpty()) {
            Log.e(TAG, "JSON пустой или null — проверь логи NetworkUtils");
            Toast.makeText(FirstActivity.this, "Ошибка загрузки локаций", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            JSONArray arr = new JSONArray(json);
            Log.d(TAG, "Получено " + arr.length() + " локаций");
            locations.clear();  // Очищаем список перед добавлением
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
            Toast.makeText(FirstActivity.this, "Локации загружены (" + locations.size() + ")", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Ошибка парсинга JSON: " + e.getMessage());
            e.printStackTrace();
            Toast.makeText(FirstActivity.this, "Ошибка обработки данных", Toast.LENGTH_SHORT).show();
        }
    }
}

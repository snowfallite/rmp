package com.example.my_app.activities;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.util.concurrent.TimeUnit;
import android.util.Log;

public class NetworkUtils {
    private static final String TAG = "NetworkUtils";

    // Mock-данные для теста (если сервер недоступен)
    private static final String MOCK_JSON = "[{\"city\":\"Москва\",\"street\":\"Ленинский проспект\",\"house\":\"1\",\"type\":\"Банк\",\"working_hours\":\"09:00-18:00\"},{\"city\":\"Санкт-Петербург\",\"street\":\"Невский проспект\",\"house\":\"10\",\"type\":\"Филиал\",\"working_hours\":\"Круглосуточно\"},{\"city\":\"Екатеринбург\",\"street\":\"Уральская\",\"house\":\"5\",\"type\":\"Отделение\",\"working_hours\":\"08:00-20:00\"}]";

    public static String getJsonString(String url) {
        int retries = 2;  // 2 попытки
        for (int i = 0; i < retries; i++) {
            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(20, TimeUnit.SECONDS)  // Увеличено до 20 сек
                        .readTimeout(20, TimeUnit.SECONDS)     // Увеличено до 20 сек
                        .build();
                Request request = new Request.Builder().url(url).build();
                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful() && response.body() != null) {
                        String json = response.body().string();
                        Log.d(TAG, "JSON загружен успешно, длина: " + json.length());
                        return json;
                    } else {
                        Log.e(TAG, "Ошибка HTTP: " + response.code() + " (попытка " + (i + 1) + ")");
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Ошибка сети (попытка " + (i + 1) + "): " + e.getMessage());
                e.printStackTrace();  // Полный stacktrace для отладки
            }
        }
        Log.w(TAG, "Все попытки провалились, возвращаю mock-данные для теста");
        return MOCK_JSON;  // Если все попытки провалились, используй mock
    }
}

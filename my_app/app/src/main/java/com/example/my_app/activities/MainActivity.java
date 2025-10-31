package com.example.my_app.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_app.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {
    private TextView tvDate, tvRates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDate = findViewById(R.id.tv_date);
        tvRates = findViewById(R.id.tv_rates);

        // Проверка интернета перед запуском AsyncTask
        if (isNetworkAvailable()) {
            new LoadRatesTask().execute();
        } else {
            tvRates.setText("Нет интернета");
            Toast.makeText(this, "Проверьте подключение к интернету", Toast.LENGTH_SHORT).show();
        }

        // Остальные клики без изменений
        findViewById(R.id.btn_locations).setOnClickListener(v ->
                startActivity(new Intent(this, FirstActivity.class)));

        findViewById(R.id.rates_container).setOnClickListener(v ->
                startActivity(new Intent(this, SecondActivity.class)));

        findViewById(R.id.btn_login).setOnClickListener(v -> showLoginDialog());
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    private void showLoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Вход");
        View view = getLayoutInflater().inflate(R.layout.dialog_login, null);
        builder.setView(view);
        builder.setPositiveButton("OK", (d, w) -> {});
        builder.setNegativeButton("Отмена", (d, w) -> d.dismiss());
        builder.show();
    }

    private class LoadRatesTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("https://www.cbr.ru/scripts/XML_daily.asp");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Android; Mobile)"); // Добавь для CBR
                connection.setConnectTimeout(10000); // Таймаут 10 сек
                connection.setReadTimeout(10000);
                connection.connect();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream is = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    reader.close();
                    connection.disconnect();
                    String xml = sb.toString();
                    android.util.Log.d("LoadRatesTask", "XML получен: " + xml.substring(0, Math.min(200, xml.length()))); // Лог для отладки
                    return parseXml(xml);
                } else {
                    android.util.Log.e("LoadRatesTask", "HTTP ошибка: " + connection.getResponseCode());
                }
            } catch (IOException e) {
                android.util.Log.e("LoadRatesTask", "Ошибка сети: " + e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                String[] parts = result.split("\\|");
                if (parts.length >= 3) {
                    tvDate.setText("Дата: " + parts[0]); // Добавь "Дата:" для ясности
                    tvRates.setText(parts[1] + "\n" + parts[2]); // Раздели на строки для лучшего вида
                } else {
                    tvRates.setText("Ошибка парсинга");
                    android.util.Log.e("LoadRatesTask", "Неверный формат result: " + result);
                }
            } else {
                tvRates.setText("Ошибка загрузки");
                Toast.makeText(MainActivity.this, "Не удалось загрузить курсы валют", Toast.LENGTH_SHORT).show();
            }
        }

        private String parseXml(String xml) {
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                InputStream inputStream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
                Document document = builder.parse(inputStream);

                Element root = document.getDocumentElement();
                String dateStr = root.getAttribute("Date");
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                Date date = sdf.parse(dateStr);
                String formattedDate = sdf.format(date);

                String usdRate = "N/A";
                String eurRate = "N/A";

                NodeList valutes = document.getElementsByTagName("Valute");
                for (int i = 0; i < valutes.getLength(); i++) {
                    Node valute = valutes.item(i);
                    if (valute.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) valute;
                        String charCode = element.getElementsByTagName("CharCode").item(0).getTextContent();
                        if ("USD".equals(charCode)) {
                            usdRate = element.getElementsByTagName("Value").item(0).getTextContent().replace(",", ".");
                        } else if ("EUR".equals(charCode)) {
                            eurRate = element.getElementsByTagName("Value").item(0).getTextContent().replace(",", ".");
                        }
                    }
                }

                android.util.Log.d("ParseXml", "USD: " + usdRate + ", EUR: " + eurRate); // Лог для проверки
                return formattedDate + "|" + "USD: " + usdRate + "|" + "EUR: " + eurRate;
            } catch (Exception e) {
                android.util.Log.e("ParseXml", "Ошибка парсинга: " + e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
    }
}

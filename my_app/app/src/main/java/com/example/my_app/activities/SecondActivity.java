package com.example.my_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.example.my_app.R;
import com.example.my_app.adapters.CurrencyAdapter;
import com.example.my_app.activities.CurrencyItem; // Предполагаю, что CurrencyItem в models (code, name, buy, sell)

public class SecondActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CurrencyAdapter adapter;
    private List<CurrencyItem> currencies = new ArrayList<>();
    private TextView tvDate;

    private final double COEFF = 0.05; // 5% маржа для покупки/продажи
    private final DecimalFormat df = new DecimalFormat("#.###"); // Формат: 65.995 (3 знака после запятой)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tvDate = findViewById(R.id.tv_date);
        recyclerView = findViewById(R.id.recycler_currencies);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CurrencyAdapter(currencies, this);
        recyclerView.setAdapter(adapter);

        // Временно: "Загрузка..." пока не подгрузим
        tvDate.setText("Дата: Загрузка...");

        // Проверка интернета перед загрузкой
        if (isNetworkAvailable()) {
            loadCurrencyRatesAsync();
        } else {
            tvDate.setText("Нет интернета");
            Toast.makeText(this, "Проверьте подключение к интернету", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    // Асинхронная загрузка курсов валют через ExecutorService
    private void loadCurrencyRatesAsync() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            List<CurrencyItem> result = loadRatesFromUrl("https://www.cbr.ru/scripts/XML_daily.asp");
            runOnUiThread(() -> {
                if (result != null && !result.isEmpty()) {
                    currencies.clear();
                    currencies.addAll(result);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(SecondActivity.this, "Курсы загружены (" + result.size() + " валют)", Toast.LENGTH_SHORT).show();
                } else {
                    tvDate.setText("Ошибка загрузки");
                    Toast.makeText(SecondActivity.this, "Не удалось загрузить курсы валют", Toast.LENGTH_SHORT).show();
                }
                executor.shutdown();
            });
        });
    }

    // Загрузка данных с URL и парсинг XML (улучшенный)
    private List<CurrencyItem> loadRatesFromUrl(String urlStr) {
        List<CurrencyItem> temp = new ArrayList<>();
        String xmlDate = null; // Для даты
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Android; Mobile)"); // Для CBR
            connection.setConnectTimeout(10000); // 10 сек
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

                Log.d("LoadRatesSecond", "XML получен (первые 200 символов): " + xml.substring(0, Math.min(200, xml.length())));

                // Парсинг XML
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

                // Извлечение даты из <Daily Date="31.10.2024">
                Element root = doc.getDocumentElement();
                xmlDate = root.getAttribute("Date"); // "31.10.2024"
                Log.d("LoadRatesSecond", "Дата из XML: " + xmlDate);

                NodeList list = doc.getElementsByTagName("Valute");
                for (int i = 0; i < list.getLength(); i++) {
                    Node node = list.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element valute = (Element) node;
                        NodeList children = valute.getChildNodes();

                        String code = "";
                        String name = "";
                        String valueStr = "";

                        // Поиск по тегам (лучше, чем switch по имени)
                        Node charCodeNode = valute.getElementsByTagName("CharCode").item(0);
                        Node nameNode = valute.getElementsByTagName("Name").item(0);
                        Node valueNode = valute.getElementsByTagName("Value").item(0);

                        if (charCodeNode != null) code = charCodeNode.getTextContent().trim();

                        if (valueNode != null) valueStr = valueNode.getTextContent().replace(",", ".").trim();

                        // Проверка на пустые данные
                        if (code.isEmpty()  || valueStr.isEmpty()) continue;

                        double cbRate;
                        try {
                            cbRate = Double.parseDouble(valueStr);
                        } catch (NumberFormatException e) {
                            Log.e("LoadRatesSecond", "Ошибка парсинга значения для " + code + ": " + valueStr);
                            continue;
                        }

                        double buy = cbRate * (1 + COEFF); // +5% (правильно: умножение, а не сложение)
                        double sell = cbRate * (1 - COEFF); // -5%

                        // Форматирование
                        String buyFormatted = df.format(buy);
                        String sellFormatted = df.format(sell);

                        temp.add(new CurrencyItem(code, name, Double.parseDouble(buyFormatted), Double.parseDouble(sellFormatted)));
                        Log.d("LoadRatesSecond", code + ": CBR=" + cbRate + ", Buy=" + buyFormatted + ", Sell=" + sellFormatted + ", NAME=" + name);
                    }
                }
            } else {
                Log.e("LoadRatesSecond", "HTTP ошибка: " + connection.getResponseCode());
            }
        } catch (IOException e) { // Добавил ParseException для даты
            Log.e("LoadRatesSecond", "Ошибка загрузки/парсинга: " + e.getMessage());
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

        // Обновление даты в UI (через runOnUiThread, но здесь возвращаем)
        if (xmlDate != null) {
            try {
                SimpleDateFormat inputSdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                Date date = inputSdf.parse(xmlDate);
                SimpleDateFormat outputSdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                final String formattedDate = outputSdf.format(date);
                runOnUiThread(() -> tvDate.setText("Дата: " + formattedDate)); // UI обновление
            } catch (ParseException e) {
                Log.e("LoadRatesSecond", "Ошибка форматирования даты: " + e.getMessage());
                runOnUiThread(() -> tvDate.setText("Дата: Ошибка"));
            }
        }

        return temp;
    }
}

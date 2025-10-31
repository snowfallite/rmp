package com.example.my_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.example.my_app.R;
import com.example.my_app.adapters.CurrencyAdapter;

public class SecondActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CurrencyAdapter adapter;
    private List<CurrencyItem> currencies = new ArrayList<>();
    private TextView tvDate;

    private final double COEFF = 0.05; // 5% маржа для покупки/продажи

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tvDate = findViewById(R.id.tv_date);
        recyclerView = findViewById(R.id.recycler_currencies);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CurrencyAdapter(currencies, this);
        recyclerView.setAdapter(adapter);

        // Устанавливаем текущую дату
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        tvDate.setText(sdf.format(new Date()));

        // Загружаем курсы валют с использованием ExecutorService
        loadCurrencyRatesAsync();
    }

    // Асинхронная загрузка курсов валют через ExecutorService
    private void loadCurrencyRatesAsync() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            List<CurrencyItem> result = loadRatesFromUrl("https://www.cbr.ru/scripts/XML_daily.asp");
            runOnUiThread(() -> {
                if (result != null) {
                    currencies.clear();
                    currencies.addAll(result);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(SecondActivity.this, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    // Загрузка данных с URL и парсинг XML
    private List<CurrencyItem> loadRatesFromUrl(String url) {
        List<CurrencyItem> temp = new ArrayList<>();
        try {
            InputStream is = new java.net.URL(url).openStream();
            InputStreamReader reader = new InputStreamReader(is, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String xml = stringBuilder.toString();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new java.io.ByteArrayInputStream(xml.getBytes()));

            NodeList list = doc.getElementsByTagName("Valute");
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                NodeList child = node.getChildNodes();

                String code = "";
                String name = "";
                String value = "";

                for (int j = 0; j < child.getLength(); j++) {
                    Node c = child.item(j);
                    switch (c.getNodeName()) {
                        case "CharCode":
                            code = c.getTextContent().trim();
                            break;
                        case "Name":
                            name = c.getTextContent().trim();
                            break;
                        case "Value":
                            value = c.getTextContent().replace(",", ".").trim();
                            break;
                    }
                }

                // Проверка на пустые данные
                if (code.isEmpty() || name.isEmpty() || value.isEmpty()) continue;

                double cbRate;
                try {
                    cbRate = Double.parseDouble(value);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    continue; // Пропускаем валюты с неправильным значением
                }

                double buy = cbRate + cbRate * COEFF;
                double sell = cbRate - cbRate * COEFF;

                temp.add(new CurrencyItem(code, name, buy, sell));
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Можно добавить логирование или вывод ошибки пользователю
        }
        return temp;
    }

}

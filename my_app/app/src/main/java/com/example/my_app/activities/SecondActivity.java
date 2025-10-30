package com.example.my_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.example.my_app.R;
import com.example.my_app.adapters.CurrencyAdapter;
public class SecondActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CurrencyAdapter adapter;
    private List<CurrencyItem> currencies = new ArrayList<>();
    private TextView tvDate;
    private final double COEFF = 0.05;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tvDate = findViewById(R.id.tv_date);
        recyclerView = findViewById(R.id.recycler_currencies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CurrencyAdapter(currencies, this);
        recyclerView.setAdapter(adapter);

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        tvDate.setText(sdf.format(new Date()));

        // Запускаем загрузку курсов валют
        new LoadRatesTask().execute("https://www.cbr.ru/scripts/XML_daily.asp");
    }

    // Исправленное имя класса без пробелов
    private class LoadRatesTask extends AsyncTask<String, Void, List<CurrencyItem>> {

        @Override
        protected List<CurrencyItem> doInBackground(String... urls) {
            try {
                // Получаем XML с сайта
                String xml = NetworkUtils.getXML(urls[0]);

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(new java.io.ByteArrayInputStream(xml.getBytes()));

                NodeList list = doc.getElementsByTagName("Valute");
                List<CurrencyItem> temp = new ArrayList<>();

                for (int i = 0; i < list.getLength(); i++) {
                    Node node = list.item(i);
                    NodeList child = node.getChildNodes();
                    String code = "", name = "", value = "";

                    for (int j = 0; j < child.getLength(); j++) {
                        Node c = child.item(j);
                        switch (c.getNodeName()) {
                            case "CharCode": code = c.getTextContent(); break;
                            case "Name": name = c.getTextContent(); break;
                            case "Value": value = c.getTextContent().replace(",", "."); break;
                        }
                    }

                    double cbRate = Double.parseDouble(value);
                    double buy = cbRate + cbRate * COEFF;
                    double sell = cbRate + cbRate * COEFF;
                    temp.add(new CurrencyItem(code, name, buy, sell));
                }

                return temp;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<CurrencyItem> result) {
            if (result == null) {
                Toast.makeText(SecondActivity.this, "Ошибка загрузки", Toast.LENGTH_SHORT).show();
                return;
            }

            currencies.clear();
            currencies.addAll(result);
            adapter.notifyDataSetChanged();
        }
    }
}

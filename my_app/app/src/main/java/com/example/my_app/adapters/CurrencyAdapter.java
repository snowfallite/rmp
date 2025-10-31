package com.example.my_app.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.my_app.R;
import com.example.my_app.activities.CurrencyItem;

import java.util.List;
import java.util.Locale;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.VH> {

    private List<CurrencyItem> items;
    private Context ctx;
    private static final String PREF = "currency_prefs";

    public CurrencyAdapter(List<CurrencyItem> items, Context ctx) {
        this.items = items;
        this.ctx = ctx;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_currency, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH h, int i) {
        CurrencyItem it = items.get(i);

        // Устанавливаем текстовые значения
        h.tvCode.setText(it.getCode());
        h.tvName.setText(it.getName());
        h.tvBuy.setText(String.format(Locale.getDefault(), "%.4f", it.getBuy()));
        h.tvSell.setText(String.format(Locale.getDefault(), "%.4f", it.getSell()));

        // Устанавливаем флаг
        h.ivFlag.setImageResource(getFlagRes(it.getCode()));

        // Стрелки изменения курса
        SharedPreferences sp = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        String key = "last_" + it.getCode();
        double last = Double.longBitsToDouble(sp.getLong(key, Double.doubleToRawLongBits(Double.NaN)));

        // Проверка изменения курса и установка стрелки
        if (!Double.isNaN(last)) {
            if (it.getBuy() > last) {
                h.tvBuyArrow.setText("↑");
                h.tvBuyArrow.setTextColor(Color.GREEN); // Зеленая стрелка
            } else if (it.getBuy() < last) {
                h.tvBuyArrow.setText("↓");
                h.tvBuyArrow.setTextColor(Color.RED); // Красная стрелка
            } else {
                h.tvBuyArrow.setText("-");
                h.tvBuyArrow.setTextColor(Color.GRAY); // Стрелка в сторону (без изменения)
            }
        } else {
            h.tvBuyArrow.setText("→");
            h.tvBuyArrow.setTextColor(Color.GRAY); // Если данных нет
        }

        // Сохраняем текущий курс покупки для следующего сравнения
        sp.edit().putLong(key, Double.doubleToRawLongBits(it.getBuy())).apply();
    }

    private int getFlagRes(String currencyCode) {
        // Берём первые две буквы валюты по умолчанию
        String countryCode = currencyCode.substring(0, 2).toLowerCase(Locale.ROOT);

        // Специальные случаи валют
        switch (currencyCode) {
            case "RON": countryCode = "ro"; break;
            case "BYN": countryCode = "by"; break;
            case "UAH": countryCode = "ua"; break;
            case "GBP": countryCode = "uk"; break;
            case "EUR": countryCode = "eu"; break;
        }

        int resId = ctx.getResources().getIdentifier("flag_" + countryCode, "drawable", ctx.getPackageName());
        if (resId != 0) return resId;
        return R.drawable.flag_ad; // дефолтный флаг
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView ivFlag;
        TextView tvCode, tvName, tvBuy, tvSell, tvBuyArrow, tvSellArrow;

        VH(View v) {
            super(v);
            ivFlag = v.findViewById(R.id.iv_flag);
            tvCode = v.findViewById(R.id.tv_code);
            tvName = v.findViewById(R.id.tv_name);
            tvBuy = v.findViewById(R.id.tv_buy);
            tvSell = v.findViewById(R.id.tv_sell);
            tvBuyArrow = v.findViewById(R.id.tv_buy_arrow); // Стрелка для курса покупки
            tvSellArrow = v.findViewById(R.id.tv_sell_arrow); // Стрелка для курса продажи (если нужно)
        }
    }
}

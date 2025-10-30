package com.example.my_app.activities;

public class CurrencyItem {
    private String code;
    private String name;
    private double buy;
    private double sell;

    public CurrencyItem(String code, String name, double buy, double sell) {
        this.code = code;
        this.name = name;
        this.buy = buy;
        this.sell = sell;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public double getBuy() { return buy; }
    public double getSell() { return sell; }
}

package com.example.my_app.base;

import android.app.Application;

public class MyApplication extends Application {
    private String resultData;


    public MyApplication() {
        super();


    }

    public String getResultData() {
        return resultData;
    }

    public void setResultData(String resultData) {
        this.resultData = resultData;
    }
}

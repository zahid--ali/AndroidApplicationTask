package com.test.androidapplicationtask.models;

/**
 * Created by Jarvis on 7/6/2017.
 */

public class CurrencyModel {
    private String name;
    private double rate;

    public CurrencyModel(String name, double rate) {
        this.name = name;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}

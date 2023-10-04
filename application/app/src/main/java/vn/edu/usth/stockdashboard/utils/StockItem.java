package vn.edu.usth.stockdashboard.utils;

import java.util.ArrayList;

public class StockItem {
    private String symbol;
    private String name;
    private String money;
    private String percentage;
    private ArrayList<CustomCandleData> chartData;
    private float closePrice = 10f;

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public ArrayList<CustomCandleData> getChartData() {
        return chartData;
    }

    public void insertChartData(CustomCandleData data) {
        chartData.add(data);
    }

    public StockItem(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
        this.money = "0";
        this.percentage = "0%";
        this.chartData = new ArrayList<>();
    }
    public StockItem(String symbol, String name, String money, String percentage) {
        this.symbol = symbol;
        this.name = name;
        this.money = money;
        this.percentage = percentage;
        this.chartData = new ArrayList<>();
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getMoney() {
        return money;
    }

    public String getPercentage() {
        return percentage;
    }

    public float getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(float closePrice) {
        this.closePrice = closePrice;
    }
}

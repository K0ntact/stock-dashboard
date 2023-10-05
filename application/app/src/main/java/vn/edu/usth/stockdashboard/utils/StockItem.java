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
        if (chartData.size() > 20) {
            chartData.remove(0);
        }

        // If the data is newer than the last data, add it to the list
        if (chartData.isEmpty() || chartData.get(chartData.size() - 1).timestamp < data.timestamp) {
            chartData.add(data);
            System.out.println("Added new data");
        }
//      If the data has the same timestamp as the last data, replace it
        else if (chartData.get(chartData.size() - 1).timestamp == data.timestamp) {
            chartData.set(chartData.size() - 1, data);
        }
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

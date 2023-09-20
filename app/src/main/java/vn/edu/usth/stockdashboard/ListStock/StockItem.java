package vn.edu.usth.stockdashboard.ListStock;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Random;

public class StockItem {
    private String symbol;
    private String name;
    private String money;
    private String percentage;
    private ArrayList<Entry> randomData;
    private float previousValue;

    public ArrayList<Entry> getRandomData() {
        return randomData;
    }

    public void generateRandomData(int numberOfDataPoints){
        randomData = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < numberOfDataPoints; i++){
            float y = random.nextFloat() * 100;
            randomData.add(new Entry((float) i,y));
        }
    }
    public StockItem(String symbol, String name, String money, String percentage) {
        this.symbol = symbol;
        this.name = name;
        this.money = money;
        this.percentage = percentage;
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

    public float getPreviousValue() {
        return previousValue;
    }

    public void setPreviousValue(float previousValue) {
        this.previousValue = previousValue;
    }

}

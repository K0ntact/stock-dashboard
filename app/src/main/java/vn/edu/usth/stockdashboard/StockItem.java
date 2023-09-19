package vn.edu.usth.stockdashboard;

public class StockItem {
    private String symbol;
    private String name;
    private String money;
    private String percentage;

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

}

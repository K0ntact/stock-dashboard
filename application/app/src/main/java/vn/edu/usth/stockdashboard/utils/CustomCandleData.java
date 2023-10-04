package vn.edu.usth.stockdashboard.utils;

public class CustomCandleData {
    public long timestamp = 0L;
    public float current_price = 0f;
    public float open = 0f;
    public float close = 0f;
    public float high = 0f;
    public float low = 0f;

    public CustomCandleData () {
    }
    public CustomCandleData (long timestamp, float current_price, float open, float close, float high, float low) {
        this.timestamp = timestamp;
        this.current_price = current_price;
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
    }
}
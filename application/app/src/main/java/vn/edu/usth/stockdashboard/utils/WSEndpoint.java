package vn.edu.usth.stockdashboard.utils;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.*;

public class WSEndpoint extends WebSocketClient {
    private List<WSDataNotify> dataNotifies = new ArrayList<>();
    private HashMap<String, CustomCandleData> stocksData = new HashMap<>();
    private long lastUpdate = 0L;
    private int interval = 60000;

    public WSEndpoint(URI serverUri, String[] symbols) {
        super(serverUri);
        assert symbols.length > 0;
        for (String symbol : symbols) {
            CustomCandleData data = new CustomCandleData();
            stocksData.put(symbol, data);
        }
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected");
        for (String symbol : stocksData.keySet()) {
            send("{\"type\":\"subscribe\",\"symbol\":\"" + symbol + "\"}");
        }
    }

    @Override
    public void onMessage(String message) {
        extractData(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        // Close the connection
        System.out.println("Disconnected. Code: " + code + ", reason: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("Error: " + ex.getMessage());
    }

    public void extractData(String message) {
        try {
            // {"data":[
            //          {"c":["1","24","12"],"p":171.465,"s":"AAPL","t":1696250801554,"v":1},
            //          {"c":null,"p":1731.1,"s":"BINANCE:ETHUSDT","t":1696250801782,"v":0.014},
            //          {"c":null,"p":1731.1,"s":"BINANCE:ETHUSDT","t":1696250801997,"v":0.0129},
            //          {"c":null,"p":1731.09,"s":"BINANCE:ETHUSDT","t":1696250802070,"v":1.001}
            //          ],"type":"trade"}
            JSONObject jsonObject = new JSONObject(message);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            HashMap<String, CustomCandleData> changeData = new HashMap<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject data = jsonArray.getJSONObject(i);
                String symbol = data.getString("s");

//                long currentTime = System.currentTimeMillis();
//                if (currentTime - lastUpdate < 30) continue;
//                lastUpdate = currentTime;

                CustomCandleData candleData = stocksData.get(symbol);
                if (candleData == null) continue;

                long timestamp = data.getLong("t");
                float current_price = (float) data.getDouble("p");
                current_price = (float) (Math.round(current_price * 100.0) / 100.0);
                candleData.current_price = current_price;

                if (current_price == candleData.close) {
                    // Skip if the current price is the same as the close price (of the last update)
                    continue;
                }

                if (timestamp - candleData.timestamp > this.interval) {
                    // If the time difference is more than 1 minute, reset the values to current price
                    candleData.open = current_price;
                    candleData.close = current_price;
                    candleData.high = current_price;
                    candleData.low = current_price;
                    candleData.timestamp = timestamp;
                } else {
                    // Else, update the values
                    if (current_price > candleData.high)
                        candleData.high = current_price;
                    else if (current_price < candleData.low) {
                        candleData.low = current_price;
                    }
                    candleData.close = current_price;   // Set current price as close price
                }

                // Create new separate object to avoid reference
                CustomCandleData newCandleData = new CustomCandleData();
                newCandleData.open = candleData.open;
                newCandleData.close = candleData.close;
                newCandleData.high = candleData.high;
                newCandleData.low = candleData.low;
                newCandleData.timestamp = candleData.timestamp;
                newCandleData.current_price = candleData.current_price;
                changeData.put(symbol, newCandleData);
                // Print symbol, current price, open, close, high, low, start_time
//                System.out.println("Symbol: " + symbol);
//                System.out.println("Current price: " + current_price);
//                System.out.println("Open: " + candleData.open);
//                System.out.println("Close: " + candleData.close);
//                System.out.println("High: " + candleData.high);
//                System.out.println("Low: " + candleData.low);

//                // Convert timestamp to date
//                Date date = new Date(candleData.timestamp);
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                System.out.println("Start time: " + sdf.format(date));
//                // Print local time
//                System.out.println("Local time: " + sdf.format(new Date()));
//                System.out.println("\n");
            }

            // Notify all WSDataNotify objects
            for (WSDataNotify wsDataNotify : dataNotifies) {
                wsDataNotify.onNewData(changeData);
            }
        }
        catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<String> getSymbols() {
        return stocksData.keySet();
    }

    public CustomCandleData getCandleData(String symbol) {
        return stocksData.get(symbol);
    }

    public void addDataNotify(WSDataNotify wsDataNotify) {
        dataNotifies.add(wsDataNotify);
    }

    public void removeDataNotify(WSDataNotify wsDataNotify) {
        dataNotifies.remove(wsDataNotify);
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getInterval() {
        return interval;
    }
}

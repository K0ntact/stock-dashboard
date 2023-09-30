package vn.edu.usth.stockdashboard.utils;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

public class ClientEndpoint extends WebSocketClient {
    private float open;

    private float close;
    private float high;
    private float low;
    private long start_time = 0;

    public ClientEndpoint(URI serverUri) {
        super(serverUri);
    }

    public ClientEndpoint(URI serverUri, Draft protocolDraft) {
        super(serverUri, protocolDraft);
    }

    public ClientEndpoint(URI serverUri, Map<String, String> httpHeaders) {
        super(serverUri, httpHeaders);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected");
        send("{\"type\":\"subscribe\",\"symbol\":\"BINANCE:ETHUSDT\"}");
    }

    @Override
    public void onMessage(String message) {
        // {"data":[{"c":null,"p":1675.58,"s":"BINANCE:ETHUSDT","t":1696073626534,"v":0.2082}],"type":"trade"}
        try {
            JSONObject jsonObject = new JSONObject(message);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject data = jsonArray.getJSONObject(i);
                float current_price = (float) data.getDouble("p");
                if (data.getLong("t") - start_time > 1000) {
                    open = current_price;
                    close = current_price;
                    high = current_price;
                    low = current_price;
                    start_time = data.getLong("t");
                } else {
                    close = current_price;
                    if (current_price > high) {
                        high = current_price;
                    }
                    else if (current_price < low) {
                        low = current_price;
                    }
                }

                // Convert start_time in timestamp to local time
                String date = sdf.format(new java.util.Date (start_time));
                System.out.println("Date: " + date);
                // Print local time
                String local_time = sdf.format(new java.util.Date (System.currentTimeMillis()));
                System.out.println("Local time: " + local_time);
                System.out.println("Open: " + open);
                System.out.println("Close: " + close);
                System.out.println("High: " + high);
                System.out.println("Low: " + low);

            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected");
    }

    @Override
    public void onError(Exception ex) {
        System.out.println("Error: " + ex.getMessage());
    }

    public float getOpen() {
        return open;
    }
    public float getClose() {
        return close;
    }
    public float getHigh() {
        return high;
    }
    public float getLow() {
        return low;
    }
    public long getStart_time() {
        return start_time;
    }
}

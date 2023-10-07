package vn.edu.usth.stockdashboard.utils;

import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DbQuery {
    private static DbQuery instance;
    private String baseUrl = "http://192.168.158.185:8080";
    private final List<RestDataNotify> dataNotifies = new ArrayList<>();

    private DbQuery() {
    }

    public static DbQuery getInstance() {
        if (instance == null) {
            instance = new DbQuery();
        }
        return instance;
    }

    public void addDataNotify(RestDataNotify notify) {
        dataNotifies.add(notify);
    }

    public void removeDataNotify(RestDataNotify notify) {
        dataNotifies.remove(notify);
    }

    public void notifyDataReceived(String response) {
        for (RestDataNotify notify : dataNotifies) {
            notify.onRestDataReceived(response);
        }
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void login(String username, String password) throws IOException {
        URL url = new URL(baseUrl + "/api/login");
        String jsonBody = "{\"username\":\"" + username + "\","
                + "\"password\":\"" + password + "\"}";
        String response = postResponseString(url, jsonBody);
        if (response == null) notifyDataReceived("ERROR");
        else notifyDataReceived(response);
    }

    public void register (
            String firstName,
            String lastName,
            String username,
            String password,
            String netassest
    ) throws IOException {
        URL url = new URL(baseUrl + "/api/register");
        // JSON format
        String jsonBody = "{\"firstname\":\"" + firstName + "\","
                + "\"lastname\":\"" + lastName + "\","
                + "\"username\":\"" + username + "\","
                + "\"password\":\"" + password + "\","
                + "\"netassest\":\"" + netassest + "\"}";
        String response = postResponseString(url, jsonBody);
        System.out.println(response == null);
        if (response == null) notifyDataReceived("ERROR");
        else notifyDataReceived(response);
    }

    public void getStockInfo(String symbol) throws IOException {
        URL url = new URL(baseUrl + "/api/stock?symbol=" + symbol);
        String response = getResponseString(url);
        if (response == null) notifyDataReceived("ERROR");
        else notifyDataReceived(response);
    }

    public void getStockInfo(String symbol, String dateRange) throws IOException {
        URL url = new URL(baseUrl + "/api/stock?symbol=" + symbol + "&dateRange=" + dateRange);
        String response = getResponseString(url);
        if (response == null) notifyDataReceived("ERROR");
        else notifyDataReceived(response);
    }

    public void getClosePrice(String symbol) throws IOException {
        URL url = new URL(baseUrl + "/api/stock/close?symbol=" + symbol);
        String response = getResponseString(url);
        if (response == null) notifyDataReceived("ERROR");
        else notifyDataReceived(response);
    }

    @Nullable
    private String getResponseString(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if(responseCode != 200) {
            System.out.println("Connection failed");
            return null;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return response.toString();
    }

    @Nullable
    private String postResponseString(URL url, String jsonBody) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept-Charset", "UTF-8");
        conn.setRequestProperty("Content-type", "application/json");
        OutputStream os = conn.getOutputStream();
        os.write(jsonBody.getBytes(StandardCharsets.UTF_8));
        os.close();
        conn.connect();

        int responseCode = conn.getResponseCode();
        if(responseCode != 200) {
            System.out.println("Connection failed");
            return null;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return response.toString();
    }
}
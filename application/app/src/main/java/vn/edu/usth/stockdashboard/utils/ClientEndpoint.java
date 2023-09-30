package vn.edu.usth.stockdashboard.utils;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Map;

public class ClientEndpoint extends WebSocketClient {

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
        System.out.println("Received: " + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected");
    }

    @Override
    public void onError(Exception ex) {
        System.out.println("Error: " + ex.getMessage());
    }
}

import { StandardWebSocketClient, WebSocketClient } from "https://deno.land/x/websocket@v0.1.4/mod.ts";

function connectWebSocket() {
    const socket: WebSocketClient = new StandardWebSocketClient('ws://localhost:8080/trade?uuid=bhhoang');
    const isReconnecting = false;
    socket.on('open', function (_event) {
        console.log('Connection opened');
        try {
            socket.send(JSON.stringify({ 'type': 'subscribe', 'symbol': 'AAPL' }));
            socket.send(JSON.stringify({ 'type': 'subscribe', 'symbol': 'BINANCE:ETHUSDT' }));
        } catch (error) {
            console.error('Error while sending subscription messages:', error);
        }
    });

    socket.on('message', function (event) {
        console.log(event.data);
    });

    socket.on('error', function (errorEvent) {

        console.error('Connection error:', errorEvent);
        console.log('Reconnecting in 5 seconds...');
        setTimeout(connectWebSocket, 5000); // Reconnect after 5 seconds
    });

}

connectWebSocket(); // Initial connection

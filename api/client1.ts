import { StandardWebSocketClient, WebSocketClient } from "https://deno.land/x/websocket@v0.1.4/mod.ts";
const socket:WebSocketClient = new StandardWebSocketClient('ws://localhost:8080/trade?uuid=bhhoang');
socket.on('open', function (_event) {
    socket.send(JSON.stringify({ 'type': 'subscribe', 'symbol': 'AAPL' }));
    socket.send(JSON.stringify({ 'type': 'subscribe', 'symbol': 'BINANCE:ETHUSDT' }))
});

socket.on('message', function (event) {
    console.log(event.data);
});
Deno.addSignalListener("SIGINT", () => {
    socket.close(1000, "Closing from client side");
    Deno.exit();
});
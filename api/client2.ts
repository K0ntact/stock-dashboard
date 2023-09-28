import { generate } from "https://deno.land/std@0.203.0/uuid/v1.ts"
const url = new URL(
    `/`,
    "ws://localhost:8080"
);
url.searchParams.append("uuid", "k0ntact");
const socket = new WebSocket(url.href.toString());
socket.addEventListener('open', function (_event) {
    socket.send(JSON.stringify({ 'type': 'subscribe', 'symbol': 'AAPL' }));
    socket.send(JSON.stringify({ 'type': 'subscribe', 'symbol': 'BINANCE:BTCUSDT' }));
    socket.send(JSON.stringify({ 'type': 'subscribe', 'symbol': 'IC MARKETS:1' }));
});

socket.addEventListener('message', function (event) {
    console.log(event.data);
})

import { generate } from 'https://deno.land/std/uuid/v1.ts'
const socket = new WebSocket('ws://localhost:8080/?uuid=' + 'k0ntact');
socket.addEventListener('open', function (_event) {
    socket.send(JSON.stringify({ 'type': 'subscribe', 'symbol': 'AAPL' }));
    socket.send(JSON.stringify({ 'type': 'subscribe', 'symbol': 'BINANCE:BTCUSDT' }));
    socket.send(JSON.stringify({ 'type': 'subscribe', 'symbol': 'IC MARKETS:1' }));
});

socket.addEventListener('message', function (event) {
    socket.addEventListener('message', function (event) {
       console.log(JSON.parse(event.data));
    })
})
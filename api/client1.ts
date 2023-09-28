import { generate } from "https://deno.land/std@0.203.0/uuid/v1.ts"
const socket = new WebSocket('ws://localhost:8080?uuid=bhhoang');
socket.addEventListener('open', function (_event) {
    socket.send(JSON.stringify({ 'type': 'subscribe', 'symbol': 'AAPL' }));
    socket.send(JSON.stringify({ 'type': 'subscribe', 'symbol': 'BINANCE:ETHUSDT' }))
});

socket.addEventListener('message', function (event) {
   console.log(event.data);
})

Deno.addSignalListener("SIGINT", () => {
    socket.close(1000, "Closing from client side");
    Deno.exit();
});
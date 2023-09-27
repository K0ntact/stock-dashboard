import { generate } from 'https://deno.land/std/uuid/v1.ts'
const socket = new WebSocket('ws://localhost:8080/?uuid=bhhoang');
socket.addEventListener('open', function (_event) {
    socket.send(JSON.stringify({ 'type': 'subscribe', 'symbol': 'AAPL' }));
});

socket.addEventListener('message', function (event) {
   console.log(JSON.parse(event.data));
})
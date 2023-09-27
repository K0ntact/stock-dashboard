import WebSocket, { WebSocketServer, WebSocketClient } from "npm:ws@8.14.2";
import "https://deno.land/std@0.202.0/dotenv/load.ts";

const clients: {
    [key: string]: { webSocket: WebSocketClient; symbol: string[] };
} = {};

if (import.meta.main) {
    const localwsServer = new WebSocketServer({ port: 8080 });

    const remoteSocket = new WebSocket(
        `wss://ws.finnhub.io?token=${Deno.env.get("TOKEN")}`
    );

    remoteSocket.on("open", () => {
        console.log("Connected to remote server");
    });

    localwsServer.on(
        "connection",
        (localSocket: WebSocketClient, req: Request) => {
            let userID = req.url?.split("?uuid=")[1]
                ? req.url?.split("?uuid=")[1]
                : 0;

            if (userID === 0) {
                console.log("Invalid user ID");
                localSocket.close();
                return;
            }
            clients[userID] = {
                webSocket: localSocket,
                symbol: [],
            };
            console.log(
                "connected: " +
                    userID +
                    " in " +
                    Object.getOwnPropertyNames(clients)
            );

            //console.log(`New client connected: ${JSON.stringify(localSocket, null, 2)}`);
            localSocket.addEventListener(
                "message",
                (message: { data: string }) => {
                    userID = req.url?.split("?uuid=")[1]
                        ? req.url?.split("?uuid=")[1]
                        : 0;
                    if (userID === 0) {
                        console.log("Invalid user ID");
                        localSocket.close();
                        return;
                    }
                    console.log(
                        `Registering client ${userID} with symbol ${message.data}`
                    );
                    const data = JSON.parse(message.data);
                    if (data.type === "subscribe") {
                        if (
                            clients[userID].symbol.indexOf(data.symbol) === -1
                        ) {
                            clients[userID].symbol.push(data.symbol);
                        }
                    } else if (data.type === "unsubscribe") {
                        const index = clients[userID].symbol.indexOf(
                            data.symbol
                        );
                        if (index !== -1) {
                            clients[userID].symbol.splice(index, 1);
                        }
                    } else {
                        console.log("Invalid message type");
                        return;
                    }

                    if (remoteSocket.readyState === WebSocket.OPEN) {
                        remoteSocket.send(message.data);
                    }
                }
            );

            remoteSocket.addEventListener(
                "message",
                (message: { data: string }) => {
                    // console.log('Received message from remote server:', message.data);
                    if (localSocket.readyState === WebSocket.OPEN) {
                        const removed_Data_Array: { s: string }[] = [];
                        // Send only-subscribed data to only-subscribed clients
                        const data = JSON.parse(message.data);
                        data.data.forEach((d: { s: string }) => {
                            if (clients[userID].symbol.includes(d.s)) {
                                removed_Data_Array.push(d);
                            }
                        });
                        if (removed_Data_Array.length > 0)
                            localSocket.send(
                                JSON.stringify({
                                    ...data,
                                    data: removed_Data_Array,
                                })
                            );
                    }
                }
            );

            localSocket.on("close", () => {
                clients[userID].webSocket.close();
                console.log(`Client ${userID} disconnected`);
            });

            remoteSocket.on("close", () => {
                console.log("Remote server disconnected");
                localSocket.close();
            });
        }
    );

    console.log("Local WebSocket server running on ws://localhost:8080");
}

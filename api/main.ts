import WebSocket, { WebSocketServer, WebSocketClient } from "npm:ws@8.14.2";
import "https://deno.land/std@0.202.0/dotenv/load.ts";
import { DataElement, Message } from "./main.d.ts";

type Client = {
    [key: string]: {
        webSocket: WebSocketClient;
        symbol: string[];
    };
};

const clients: Client = new Object() as Client;

const registeredSymbols = new Set<string>();

if (import.meta.main) {
    const base = "ws://localhost:8080";
    const localwsServer = new WebSocketServer({ port: 8080 });
    const url = new URL(
        `?token=${Deno.env.get("TOKEN")}`,
        "wss://ws.finnhub.io"
    );
    const remoteSocket = new WebSocket(url.toString());

    remoteSocket.on("open", () => {
        console.log("Connected to remote server");
    });

    localwsServer.on(
        "connection",
        (localSocket: WebSocketClient, req: Request) => {
            console.log("New client connected");
            
            let param = new URL(req.url as string, base);

            let userID = param.searchParams.get("uuid");

            if (!userID) {
                console.log("Invalid user ID");
                localSocket.close();
                return;
            }

            clients[userID] = {
                webSocket: localSocket,
                symbol: [],
            };

            //console.log(`New client connected: ${JSON.stringify(localSocket, null, 2)}`);
            localSocket.addEventListener("message", (message: Message) => {
                param = new URL(req.url as string, base);
                userID = param.searchParams.get("uuid");

                if (!userID) {
                    console.log("Invalid user ID");
                    localSocket.close();
                    return;
                }
                const data = JSON.parse(message.data);
                if (data.type === "subscribe") {
                    if (clients[userID].symbol.indexOf(data.symbol) === -1) {
                        clients[userID].symbol.push(data.symbol);
                        if (registeredSymbols.has(data.symbol)) return;
                        registeredSymbols.add(data.symbol);
                    }
                } else if (data.type === "unsubscribe") {
                    const index = clients[userID].symbol.indexOf(data.symbol);
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
            });

            remoteSocket.addEventListener("message", (message: Message) => {
                // console.log('Received message from remote server:', message.data);
                if (localSocket.readyState === WebSocket.OPEN) {
                    const removed_Data_Array: Array<DataElement> = [];
                    // Send only-subscribed data to only-subscribed clients
                    const data = JSON.parse(message.data);
                    if (data.type !== "trade") return;

                    data.data.forEach((d: DataElement) => {
                        if (!userID) return;
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
            });

            localSocket.on("close", () => {
                if (!userID) return;
                clients[userID].webSocket.close(1000, "Closing from server");
                clients[userID].webSocket.terminate();
                console.log(`Client ${userID} disconnected`);
                delete clients[userID];
            });

            remoteSocket.on("close", () => {
                console.log("Remote server disconnected");
                localSocket.close();
            });
        }
    );

    console.log("Local WebSocket server running on ws://localhost:8080");
}

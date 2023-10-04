import { Context } from "https://deno.land/x/oak@v12.6.1/mod.ts";
import {
    WebSocketClient,
    StandardWebSocketClient,
} from "https://deno.land/x/websocket@v0.1.4/mod.ts";
import { DataElement, Message } from "./trade.d.ts";

type Client = {
    [key: string]: {
        webSocket: WebSocket;
        symbol: string[];
    };
};

const clients: Client = new Object() as Client;

const registeredSymbols = new Set<string>();

const url = new URL(`?token=${Deno.env.get("TOKEN")}`, "wss://ws.finnhub.io"); // wss://ws.finnhub.io?token=...

let remoteSocket: WebSocketClient = new StandardWebSocketClient(
    url.toString()
);

remoteSocket.on("open", () => {
    console.log("Connected to remote server");
});

export const tradeEventHandler = (context: Context) => {
    const userID = context.request.url.searchParams.get("uuid");
    if (!userID) {
        console.log("Invalid user ID");
        return;
    }
    if (!context.isUpgradable) {
        context.throw(501);
    }
    const localSocket = context.upgrade();
    localSocket.onopen = () => {
        console.log("Connected to client " + userID);
        if (localSocket.readyState === WebSocket.OPEN){
            localSocket.send("Hello from server!");
        }
    };
    localSocket.addEventListener("message", (message: Message) => {
        if (!userID) {
            console.log("Invalid user ID");
            return;
        }
        const data = JSON.parse(message.data);
        if (!clients[userID]) {
            clients[userID] = {
                webSocket: localSocket,
                symbol: [],
            };
        }
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

        if (!remoteSocket.isClosed) {
            remoteSocket.send(message.data);
        }
    });

    remoteSocket.addListener("message", (message: Message) => {
        // console.log('Received message from remote server:', message.data);
        const removed_Data_Array: Array<DataElement> = [];
        // Send only-subscribed data to only-subscribed clients
        const data = JSON.parse(message.data);
        if (data.type !== "trade") return;

        data.data.forEach((d: DataElement) => {
            if (!userID) return;
            if (!clients[userID]) return;
            if (clients[userID].symbol.includes(d.s)) {
                removed_Data_Array.push(d);
            }
        });

        if (removed_Data_Array.length > 0){
            if (localSocket.readyState === WebSocket.OPEN)
                localSocket.send(
                    JSON.stringify({
                        ...data,
                        data: removed_Data_Array,
                    })
                );
        }
    });

    localSocket.onclose = () => {
        if (!userID) return;
        clients[userID].webSocket.close(1000, "Closing from server");
        console.log(`Client ${userID} disconnected`);
        delete clients[userID];
    };

    remoteSocket.addListener("close", () => {
        console.log("Remote server disconnected, reconnecting...");
        remoteSocket = new StandardWebSocketClient(url.toString());
        remoteSocket.on("open", () => {
            console.log("Connected to remote server");
        });
    });
};

import { Context } from "https://deno.land/x/oak@v12.6.1/mod.ts";
import {
    WebSocketClient,
    StandardWebSocketClient,
} from "https://deno.land/x/websocket@v0.1.4/mod.ts";
import { DataElement, Message } from "./trade.d.ts";
import { Client } from "./trade.d.ts";

const clients: Client = new Object() as Client;
const registeredSymbols = new Set<string>();
const url = new URL(`?token=${Deno.env.get("TOKEN")}`, "wss://ws.finnhub.io");
const remoteSocket: WebSocketClient = new StandardWebSocketClient(
    url.toString()
);
/**
 * @overview Handle trade route
 *
 * @description
 * This function will handle websocket connection from client and finnhub server
 *
 * @param context
 *
 * @returns None
 */
export const tradeEventHandler = (context: Context): void => {
    const userID = context.request.url.searchParams.get("uuid");
    if (!userID) {
        console.log("Invalid user ID");
        return;
    }
    if (!context.isUpgradable) {
        context.throw(501);
    }
    const localSocket = context.upgrade();

    localSocketEventHandler(localSocket, userID);
    remoteSocketEventHandler(localSocket, userID);
};

/**
 * @overview Handle remote socket event
 * @desscription
 * This function will handle remote socket event from finnhub server
 * @effect
 * Send only-subscribed data to only-subscribed clients in clients object localSocket
 * @param localSocket
 * @param userID
 */
const remoteSocketEventHandler = (
    localSocket: WebSocket,
    userID: string
): void => {
    remoteSocket.on("open", () => {
        console.log("Connected to remote server");
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

        if (removed_Data_Array.length <= 0) return;

        if (localSocket.readyState !== WebSocket.OPEN) return;
        const sendData = { ...data, data: removed_Data_Array };
        try {
            localSocket.send(JSON.stringify(sendData));
        } catch (error) {
            console.error("Error while sending data to client:", error);
        }
    });

    remoteSocket.on("error", (error) => {
        console.log("Error:", error);
        console.log("Reconnecting in 5 seconds...");
        setTimeout(() => {
            remoteSocketEventHandler(localSocket, userID);
        }, 5000);
    });
};

/**
 * @overview Handle local socket event
 * @desscription
 * This function will handle local socket event from client
 * @effect
 * Send only-subscribed data to only-subscribed clients in clients object localSocket
 * @param localSocket
 * @param userID
 * @returns None
 */
const localSocketEventHandler = (
    localSocket: WebSocket,
    userID: string
): void => {
    localSocket.onopen = () => {
        try {
            if (!userID) return;
            if (clients[userID]) return console.log("Client already connected");
            console.log("Connected to client " + userID);
            if (localSocket.readyState !== WebSocket.OPEN) return;
            localSocket.send("Hello from server!");
        } catch (error) {
            console.log("Error while sending message to client:", error);
        }
    };
    localSocket.addEventListener("message", (message: Message) => {
        if (localSocket.readyState !== WebSocket.OPEN || localSocket.readyState === WebSocket.CONNECTING) return;
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

        switch (data.type) {
            case "subscribe": {
                if (clients[userID].symbol.indexOf(data.symbol) === -1) {
                    clients[userID].symbol.push(data.symbol);
                    if (registeredSymbols.has(data.symbol)) return;
                    registeredSymbols.add(data.symbol);
                }
                break;
            }
            case "unsubscribe": {
                const index = clients[userID].symbol.indexOf(data.symbol);
                if (index !== -1) {
                    clients[userID].symbol.splice(index, 1);
                }
                break;
            }
            default: {
                console.log("Invalid message type");
                return;
            }
        }
        if (remoteSocket.isClosed) return;
        remoteSocket.send(message.data);
    });

    localSocket.onclose = () => {
        if (!userID) return;
        if (!clients[userID])
            return console.log("RIP client code 1: " + userID);
        if (typeof clients[userID].webSocket === "undefined")
            return console.log("RIP client code 2: " + userID);
        if (clients[userID].webSocket.readyState !== WebSocket.CLOSED) return;
        clients[userID].webSocket.close();
        console.log(`Client ${userID} disconnected`);
        delete clients[userID];
    };

    localSocket.onerror = (error) => {
        console.log("Error:", error);
    };
};

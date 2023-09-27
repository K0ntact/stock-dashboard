import WebSocket, { WebSocketServer } from "npm:ws@8.14.2";
import "https://deno.land/std@0.202.0/dotenv/load.ts";

if (import.meta.main) {
  
    const localwsServer = new WebSocketServer({ port: 8080 });

    const remoteSocket = new WebSocket(`wss://ws.finnhub.io?token=${Deno.env.get("TOKEN")}`);

    remoteSocket.on("open", () => {
        console.log("Connected to remote server");
    });

    localwsServer.on(
        "connection",
        (localSocket: {
            addEventListener: (
                arg0: string,
                arg1: (message: { data: unknown }) => void
            ) => void;
            readyState: unknown;
            send: (arg0: unknown) => void;
            on: (arg0: string, arg1: () => void) => void;
            close: () => void;
        }) => {
            console.log("Client connected to local server");

            localSocket.addEventListener(
                "message",
                (message: { data: unknown }) => {
                    console.log(
                        "Received message from local client:",
                        message.data
                    );
                    if (remoteSocket.readyState === WebSocket.OPEN) {
                        remoteSocket.send(message.data);
                    }
                }
            );

            remoteSocket.addEventListener(
                "message",
                (message: { data: unknown }) => {
                    //console.log('Received message from remote server:', message.data);
                    if (localSocket.readyState === WebSocket.OPEN) {
                        localSocket.send(message.data);
                    }
                }
            );

            localSocket.on("close", () => {
                console.log("Local client disconnected");
                remoteSocket.close();
            });
            remoteSocket.on("close", () => {
                console.log("Remote server disconnected");
                localSocket.close();
            });
        }
    );

    console.log("Local WebSocket server running on ws://localhost:8080");
}

// import WebSocket, { WebSocketServer, WebSocketClient } from "npm:ws@8.14.2";
import "https://deno.land/std@0.202.0/dotenv/load.ts";
import { Application, Router } from "https://deno.land/x/oak@v12.6.1/mod.ts";
import { DataBase } from "./router/database.ts";
import { tradeEventHandler } from "./router/trade.ts";

if (import.meta.main) {
    const db_name = "project";
    const db_schema_path = "./model";
    const db_hostname = "127.0.0.1";
    const db_username = "root";
    const db_password = "";
    const db = new DataBase(
        db_name,
        db_schema_path,
        db_hostname,
        db_username,
        db_password
    );
    db.init();

    const router = new Router();
    const app = new Application();
    router.post("/api/register", async (context) => {
        const body = context.request.body();
        const value = await body.value;
        const { username, password } = value;
        const query = `INSERT INTO user (username, password) VALUES ('${username}', '${password}')`;
        await db.query(query);
        context.response.body = "OK";
    });

    router.get("/trade", (context) => {
        tradeEventHandler(context);
    });

    app.use(router.routes());
    app.use(router.allowedMethods());
    app.listen({ port: 8080 });
}

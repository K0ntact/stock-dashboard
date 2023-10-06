// import WebSocket, { WebSocketServer, WebSocketClient } from "npm:ws@8.14.2";
import "https://deno.land/std@0.202.0/dotenv/load.ts";
import { Application, Context, Router } from "https://deno.land/x/oak@v12.6.1/mod.ts";
import { DataBase } from "./router/database.ts";
import { tradeEventHandler } from "./router/trade.ts";

if (import.meta.main) {
    const db_name = "project";
    const db_schema_path = "./model";
    const db_hostname = "localhost";
    const db_username = "root";
    const db_password = "root";
    const db = new DataBase(
        db_name,
        db_schema_path,
        db_hostname,
        db_username,
        db_password
    );
    db.connect();

    const router = new Router();
    const app = new Application();
    router.post("/api/register", async (context) => {
        console.log("Register");
        const body = context.request.body();
        const value = await body.value;
        console.log(value);
        
        const { firstname, lastname, username, password, netassest } = value;
        console.log(firstname, lastname, username, password, netassest);
    
        // const pwdhash = hash(password);
        const query = `INSERT INTO users (firstname, lastname, username, pwdhash, netassest) VALUES ('${firstname}', '${lastname}', '${username}', '${password}', '${netassest}')`;
        console.log(query);
        
        const result = await db.query(query);
        if (result) {
            context.response.body = "OK";
        }
        else {
            context.response.body = "FAIL";
        }
    });

    router.post("/api/login", async (context) => {
        const body = context.request.body();
        const value = await body.value;
        const { username, password } = value;
        // const pwdhash = hash(password);
        const query = `SELECT * FROM users WHERE username='${username}' AND pwdhash='${password}'`;
        const result = await db.execute(query);
        if (result != null) {
            context.response.body = result;
        }
        else {
            context.response.body = null;
        }
    });

    // Get detail of a stock
    router.get("/api/stock", async (context: Context) => {
        console.log("Get stock detail");
        const symbol = context.request.url.searchParams.get("symbol");
        const query = `SELECT * FROM stock WHERE symbol='${symbol}'`;
        const result = await db.execute(query);
    
        if (result != null) {
            context.response.body = result;
        }
        else {
            context.response.body = null;
        }
    });

    router.get("/trade", (context) => {
        try {
            tradeEventHandler(context);
        } catch (error) {
            console.log(error);
        }
    });

    app.use(router.routes());
    app.use(router.allowedMethods());
    app.listen({ port: 8080 });
}

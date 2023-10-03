import { Client } from "https://deno.land/x/mysql@v2.12.1/mod.ts";
export interface DataBase {
    dbName: string;
    schemaPath: string;
    hostname: string;
    username: string;
    password: string;
    client: Client;

    init(): Promise<void>;
    getClient(): Client;
    close(): Promise<void>;
    query(sql: string): Promise<boolean>;
    execute(sql: string): Promise<boolean>;
}
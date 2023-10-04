import { Client } from "https://deno.land/x/mysql@v2.12.1/mod.ts";

/**
 * Initialize the database by creating a client, connecting to the server,
 * creating the database if it doesn't exist, and setting up tables.
 * @param dbName - The name of the database to create and use.
 * @param schemaPath - The file path to the SQL schema file.
 * @param hostname - The hostname of the MySQL server.
 * @param username - The username of the MySQL server.
 * @param password - The password of the MySQL server.
 * @returns A promise that resolves to a connected and initialized MySQL client.
 */
export class DataBase extends Object implements DataBase {
  private dbName : string;
  private schemaPath : string;
  private hostname : string;
  private username : string;
  private password : string;
  private client : Client;

  constructor(dbName : string, schemaPath : string, hostname : string, username : string, password : string) {
  super();
    this.dbName = dbName;
    this.schemaPath = schemaPath;
    this.hostname = hostname;
    this.username = username;
    this.password = password;
    this.client = new Client();
  }

  async init() {
    await this.client.connect({
      hostname: this.hostname,
      username: this.username,
      password: this.password,
    });
    await this.client.execute(`CREATE DATABASE IF NOT EXISTS ${this.dbName}`);
    await this.client.execute(`USE ${this.dbName}`);
    const queries: string[] = [];
    const decoder = new TextDecoder("utf-8");
    for (const file of Deno.readDirSync(this.schemaPath)) {
      if (file.isFile && file.name.endsWith(".sql")) {
        const data = Deno.readFileSync(`${this.schemaPath}/${file.name}`);
        queries.push(decoder.decode(data));
      }
    }
    for (const query of queries) {
      await this.client.execute(query);
    }
  }

  getClient() {
    return this.client;
  }

  async close() {
    await this.client.close();
  }

  async query(sql : string): Promise<boolean> {
    return await this.client.query(sql);
  }

  async execute(sql : string): Promise<boolean> {
    try{
      await this.client.execute(sql);
      return true;
    } catch (error) {
      console.log(error);
      return false;
    }
  }
  public get db_name() : string {
    return this.dbName;
  }

  public get schema_path() : string {
    return this.schemaPath;
  }

  public get host() : string {
    return this.hostname;
  }

  public get user() : string {
    return this.username;
  }

  public get pass() : string {
    return this.password;
  }
  
}
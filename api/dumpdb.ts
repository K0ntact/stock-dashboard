import { DataBase } from "./router/database.ts";
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



// Read the json from model/test.json
const decoder = new TextDecoder("utf-8");
const data = await Deno.readFile("Output.json");
const json = decoder.decode(data);
const array = JSON.parse(json);


// for (let i = 0; i < array.length; i++) {
//     let { Symbol, Name, Description, Sector, Address, MarketCapitalization, PERatio, PEGRatio, EPS } = array[i];

//     // If any of the data is "None", set it to null
//     if (Symbol == "None") {
//         Symbol = null;
//     }
//     if (Name == "None") {
//         Name = null;
//     }
//     if (Description == "None") {
//         Description = null;
//     }
//     if (Sector == "None") {
//         Sector = null;
//     }
//     if (Address == "None") {
//         Address = null;
//     }
//     if (MarketCapitalization == "None") {
//         MarketCapitalization = null;
//     }
//     if (PERatio == "None") {
//         PERatio = null;
//     }
//     if (PEGRatio == "None") {
//         PEGRatio = null;
//     }
//     if (EPS == "None") {
//         EPS = null;
//     }

//     const yearHigh = array[i]["52WeekHigh"];
//     const yearLow = array[i]["52WeekLow"];
//     const movingavg50 = array[i]["50DayMovingAverage"];
//     const movingavg200 = array[i]["200DayMovingAverage"];

// let newDescription = "";
// //Handle special characters in Description
// if (Description != null) {
//     for (let j = 0; j < Description.length; j++) {
//         if (Description[j] == "'") {
//             newDescription += "\\'";
//         }
//         else {
//             newDescription += Description[j];
//         }
//     }
// }

// symbolList.push(Symbol);
// const query = `INSERT INTO stock (symbol, companyname, description, sector, address, mktcap, yearhigh, yearlow, esp, peratio, pegratio, movingavg50, movingavg200) VALUES ('${Symbol}', '${Name}', '${newDescription}', '${Sector}', '${Address}', '${MarketCapitalization}', '${yearHigh}', '${yearLow}', '${EPS}', '${PERatio}', '${PEGRatio}', '${movingavg50}', '${movingavg200}')`;

// console.log("Adding " + Symbol);


// //catch error
// try {
//     const result = await db.query(query);
//     if (result) {
//         console.log("OK");
//     }
//     else {
//         console.log("FAIL" + Symbol);
//     }
// } catch (error) {
//     console.log(error);

// }
// }
        
const symbolList = [
    "AAPL", "SBUX", "NKE",   "TSLA",
    "AMZN", "META", "GOOGL", "MSFT",
    "NVDA", "PYPL", "TSM",   "V",
    "WMT",  "ADBE", "CMCSA", "NFLX",
    "PEP",  "CSCO",  "AVGO",
    "TMUS", "QCOM", "TXN",   "CHTR",
    "AMGN", "GILD", "INTU",  "BKNG",
    "ISRG", "ADP",  "VRTX",
    "MDLZ", "REGN", "ATVI",  "CSX",
    "ADSK", "ADI"
];
        
// 1 year of data
const startDate = Math.floor(Date.now()/1000) - 365 * 24 * 60 * 60;
const endDate = Date.now();
const API_KEY = "ckg4o71r01qknh1jmve0ckg4o71r01qknh1jmveg";
const SYMBOL = "ADI";

const response = await fetch(
    "https://finnhub.io/api/v1/stock/candle?symbol=" +
    SYMBOL +
    "&resolution=1&from=" +
    startDate +
    "&to=" +
    endDate +
    "&token=" +
    API_KEY
    );
    const resdata = await response.json();
const open = resdata.o;
const high = resdata.h;
const low = resdata.l;
const close = resdata.c;
const timestamp = resdata.t;
const pos = symbolList.indexOf(SYMBOL) + 1;

console.log(resdata);

console.log("Adding " + SYMBOL);

for (let i = 0; i < timestamp.length; i++) {
    await db.execute(
        `INSERT INTO candle (timestamp, open, close, high, low, stock_id) VALUES ('${timestamp[i]}', '${open[i]}', '${close[i]}', '${high[i]}', '${low[i]}', '${pos}')`
        );
    }
            

// for (const SYMBOL in symbolList) {
//     const response = await fetch(
//         "https://finnhub.io/api/v1/stock/candle?symbol=" +
//             SYMBOL +
//             "&resolution=1&from=" +
//             startDate +
//             "&to=" +
//             endDate +
//             "&token=" +
//             API_KEY
//     );
//     const resdata = await response.json();
//     const open = resdata.o;
//     const high = resdata.h;
//     const low = resdata.l;
//     const close = resdata.c;
//     const timestamp = resdata.t;

//     console.log(resdata);
    

//     const pos = symbolList.indexOf(SYMBOL) + 1;

//     for (let i = 0; i < timestamp.length; i++) {
//         await db.execute(
//             `INSERT INTO candle (timestamp, open, close, high, low, stock_id) VALUES ('${timestamp[i]}', '${open[i]}', '${close[i]}', '${high[i]}', '${low[i]}', '${pos}')`
//         );
//     }
// }
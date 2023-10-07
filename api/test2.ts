import { Client } from "https://deno.land/x/mysql@v2.12.1/mod.ts";

// Define your MySQL connection options
const client = await new Client().connect({
    hostname: "localhost",
    username: "root",
    password: "",
    db: "project",
});

// Define your API key and stock symbol
const API_KEY = "ckeomppr01qvl18vgirgckeomppr01qvl18vgis0";

const API_KEY2 = ['YJ3AL9H5EF8JSBSC','247Y77SPENSD9U99','KQNF4WJ5PGS9RCJ1', '2MMAQI6J2OIFCLP4', 'SPXETUV0RXOPHDRX'];

const symbols = [
    "ADBE",
    "CMCSA",
    "NFLX",
    "INTC",
    "PEP",
    "CSCO",
    "AVGO",
    "TMUS",
    "QCOM",
    "TXN",
    "CHTR",
    "AMGN",
    "AMD",
    "GILD",
    "INTU",
    "BKNG",
    "FISV",
    "ISRG",
    "ADP",
    "VRTX",
    "MU",
    "MDLZ",
    "REGN",
    "ATVI",
    "CSX",
    "ILMN",
    "ADSK",
    "ADI"
];

// Define the start and end dates for the historical data
let startDate: string | number = "2013-06-10";
let endDate: string | number = "2023-06-10";

let count = 0;
let index = 0;

const result: Array<object> = [];

// Convert the dates to UNIX timestamps
startDate = new Date(startDate).getTime();
endDate = new Date(endDate).getTime();

// Overview symbols from alphavanage.co then insert into database
for (let i = 0; i < symbols.length; i++) {
    if (count === 5) {
        index++;
        count = 0;
    }
    const response = await fetch(
        "https://www.alphavantage.co/query?function=OVERVIEW&symbol=" +
            symbols[i] +
            "&apikey=" +
            API_KEY2[index]
    );
    const data = await response.json();
    if (data.Note) {
        index++;
        i--;
        continue;
    }
    result.push(data);    
    count++;
}

Deno.writeTextFile("Output.json", JSON.stringify(result, null, 4));

// // Historical data from finnhub.io then insert into database
// for (const SYMBOL in symbols) {
//     const response = await fetch(
//         "https://finnhub.io/api/v1/stock/candle?symbol=" +
//             SYMBOL +
//             "&resolution=D&from=" +
//             startDate +
//             "&to=" +
//             endDate +
//             "&token=" +
//             API_KEY
//     );
//     const data = await response.json();
//     const open = data.o;
//     const high = data.h;
//     const low = data.l;
//     const close = data.c;
//     const volume = data.v;
//     const timestamp = data.t;

//     for (let i = 0; i < timestamp.length; i++) {
//         await client.execute(
//             `INSERT INTO candle (eid, symbol, timestamp, open, high, low, close, volume) VALUES (NULL, '${SYMBOL}', '${timestamp[i]}', '${open[i]}', '${high[i]}', '${low[i]}', '${close[i]}', '${volume[i]}')`
//         );
//     }
// }

// Close the MySQL connection
await client.close();
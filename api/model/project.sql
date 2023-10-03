-- schema.sql

-- Create User table
CREATE TABLE IF NOT EXISTS User (
  eid BIGINT PRIMARY KEY,
  firstname TEXT NOT NULL,
  lastname TEXT NOT NULL,
  username TEXT NOT NULL,
  pwdhash TEXT NOT NULL,
  netassest BIGINT
);

-- Create UserStock table
CREATE TABLE IF NOT EXISTS UserStock (
  userid BIGINT REFERENCES User(eid),
  stock_id INT,
  eid BIGINT PRIMARY KEY
);

-- Create Stock table
CREATE TABLE IF NOT EXISTS Stock (
  eid INT PRIMARY KEY,
  symbol VARCHAR(10),
  companyname TEXT NOT NULL,
  description TEXT,
  sector TEXT,
  address TEXT,
  mktcap INT,
  yearhigh FLOAT,
  yearlow FLOAT,
  eps FLOAT,
  peratio FLOAT,
  pegratio FLOAT,
  movingavg50d FLOAT,
  movingavg200d FLOAT
);

-- Create Candle table
CREATE TABLE IF NOT EXISTS Candle (
  eid INT REFERENCES Stock(eid),
  timestamp BIGINT NOT NULL,
  open FLOAT NOT NULL,
  close FLOAT NOT NULL,
  high FLOAT NOT NULL,
  low FLOAT NOT NULL,
  stock_id INT REFERENCES Stock(eid)
);

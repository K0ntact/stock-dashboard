CREATE SCHEMA Project;
USE Project;

CREATE TABLE Users ( 
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    firstname TEXT NOT NULL , 
    lastname TEXT NOT NULL,
    username TEXT NOT NULL,
    pwdhash TEXT NOT NULL , 
    netassest BIGINT NOT NULL
);

CREATE TABLE Stock (
    id INT PRIMARY KEY AUTO_INCREMENT, 
    symbol VARCHAR(10) NOT NULL,
    companyname TEXT NOT NULL,
    description TEXT, 
    sector TEXT,
    address TEXT,
    mktcap BIGINT,
    yearhigh FLOAT,
    yearlow FLOAT, 
    esp FLOAT,
    peratio FLOAT, 
    pegratio FLOAT, 
    movingavg50 FLOAT,
    movingavg200 FLOAT
);

CREATE TABLE UserStock (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL, 
    stock_id INT NOT NULL,
    volume BIGINT NOT NULL,
    buyprice FLOAT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (stock_id) REFERENCES STOCK(id)
);

CREATE TABLE Candle (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    timestamp BIGINT NOT NULL,
    open FLOAT NOT NULL,
    close FLOAT NOT NULL,
    high FLOAT NOT NULL,
    low FLOAT NOT NULL,
    stock_id INT NOT NULL,
    FOREIGN KEY (stock_id) REFERENCES STOCK(id)
);
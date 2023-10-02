CREATE SCHEMA Project;
USE Project;

CREATE TABLE User ( 
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    firstname TEXT NOT NULL , 
    lastname TEXT NOT NULL,
    username TEXT NOT NULL,
    pwdhash TEXT NOT NULL , 
    netassest BIGINT NOT NULL
);

CREATE TABLE UserStock (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL, 
    stock_id INT NOT NULL,
    volume BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES USER(id),
    FOREIGN KEY (stock_id) REFERENCES STOCK(id),
);

CREATE TABLE Stock (
    id INT PRIMARY KEY AUTO_INCREMENT, 
    symbol CHAR NOT NULL,
    companyname TEXT NOT NULL,
    description TEXT NOT NULL, 
    sector TEXT NOT NULL,
    address TEXT NOT NULL,
    mktcap INT NOT NULL,
    yearhigh FLOAT NOT NULL,
    yearlow FLOAT NOT NULL, 
    esp FLOAT NOT NULL,
    peratio FLOAT NOT NULL, 
    pegratio FLOAT NOT NULL, 
    movingavg50 FLOAT NOT NULL,
    movingavg200 FLOAT NOT NULL
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
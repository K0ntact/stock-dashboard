CREATE TABLE IF NOT EXISTS Stock (
    eid INT AUTO_INCREMENT PRIMARY KEY,
    symbol VARCHAR(10) NOT NULL UNIQUE,
    companyname VARCHAR(255) NOT NULL,
    description TEXT,
    sector VARCHAR(255),
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
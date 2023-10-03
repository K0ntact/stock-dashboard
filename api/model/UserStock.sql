CREATE TABLE IF NOT EXISTS UserStock (
  eid BIGINT AUTO_INCREMENT PRIMARY KEY,
  userid BIGINT,
  stock_id INT,
  FOREIGN KEY (userid) REFERENCES Users(eid),
  FOREIGN KEY (stock_id) REFERENCES Stock(eid)
);
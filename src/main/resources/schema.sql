CREATE TABLE IF NOT EXISTS employee (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    department  VARCHAR(50)  NOT NULL,
    salary      DOUBLE       NOT NULL
);

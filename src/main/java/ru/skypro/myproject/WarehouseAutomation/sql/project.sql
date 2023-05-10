CREATE TABLE Warehouse (
                           id SERIAL PRIMARY KEY,
                           socksByColor JSON,
                           socksByCottonPart JSON
);

CREATE TABLE Socks (
                       id SERIAL PRIMARY KEY,
                       color VARCHAR(255) NOT NULL,
                       cottonPart INTEGER NOT NULL,
                       quantity INTEGER NOT NULL,
                       warehouse_id INTEGER REFERENCES warehouse(id) ON DELETE CASCADE
);


CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE USERS (
    user_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    email VARCHAR(30) UNIQUE NOT NULL
);

CREATE TABLE ORDERS(
    order_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    user_id UUID,
    date TIMESTAMP NOT NULL,
    status VARCHAR(30) NOT NULL,
    paid BOOLEAN NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,

    FOREIGN KEY (user_id) REFERENCES USERS(user_id)
);
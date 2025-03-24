CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE ORDER_PRODUCTS(
    order_products_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    order_id UUID,
    product_id UUID,
    quantity INTEGER NOT NULL,

    FOREIGN KEY (order_id) REFERENCES ORDERS(order_id),
    FOREIGN KEY (product_id) REFERENCES PRODUCTS(product_id)
);
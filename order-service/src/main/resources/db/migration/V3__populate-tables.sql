CREATE EXTENSION IF NOT EXISTS "pgcrypto";

INSERT INTO USERS (user_id, name, email) values (gen_random_uuid(), 'Alan Ensina', 'alanensina@gmail.com');
INSERT INTO USERS (user_id, name, email) values (gen_random_uuid(), 'John Doe', 'johndoe@gmail.com');
INSERT INTO USERS (user_id, name, email) values (gen_random_uuid(), 'Mary Jane', 'maryjane@gmail.com');
INSERT INTO USERS (user_id, name, email) values (gen_random_uuid(), 'Peter Parker', 'peterparker@gmail.com');
INSERT INTO USERS (user_id, name, email) values (gen_random_uuid(), 'Elon Sucks', 'elonsucks@gmail.com');

INSERT INTO PRODUCTS (product_id, name, price, available, stock) values (gen_random_uuid(), 'PS5 Slim Digital Console', 449.00, true, 50);
INSERT INTO PRODUCTS (product_id, name, price, available, stock) values (gen_random_uuid(), 'PS5 Slim Disk Console', 549.00, true, 50);
INSERT INTO PRODUCTS (product_id, name, price, available, stock) values (gen_random_uuid(), 'Apple iPhone 16 Pro', 1239.00, true, 150);
INSERT INTO PRODUCTS (product_id, name, price, available, stock) values (gen_random_uuid(), 'Apple iPhone 16 Pro Max', 1489.00, true, 150);
INSERT INTO PRODUCTS (product_id, name, price, available, stock) values (gen_random_uuid(), 'Apple MacBook Air M4', 1249.00, true, 100);
INSERT INTO PRODUCTS (product_id, name, price, available, stock) values (gen_random_uuid(), 'Apple MacBook Pro M4', 1949.00, true, 100);
INSERT INTO PRODUCTS (product_id, name, price, available, stock) values (gen_random_uuid(), 'Nintendo Switch OLED', 349.00, false, 0);
INSERT INTO PRODUCTS (product_id, name, price, available, stock) values (gen_random_uuid(), 'PS4 Slim', 249.00, false, 0);
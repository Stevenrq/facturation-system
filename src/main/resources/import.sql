-- Clientes
INSERT INTO customers (name, last_name, email, created_at, photo) VALUES ('Andrés', 'Rodríguez', 'andresrodriguez77@gmail.com', NOW(), '');
INSERT INTO customers (name, last_name, email, created_at, photo) VALUES ('Steven', 'Ricardo', 'stevenr@gmail.com', NOW(), '');
INSERT INTO customers (name, last_name, email, created_at, photo) VALUES ('John', 'Doe', 'johndoe@gmail.com', NOW(), '');
INSERT INTO customers (name, last_name, email, created_at, photo) VALUES ('Jane', 'Doe', 'janedoe@gmail.com', NOW(), '');
INSERT INTO customers (name, last_name, email, created_at, photo) VALUES ('Bob', 'Smith', 'bobsmith@gmail.com', NOW(), '');
INSERT INTO customers (name, last_name, email, created_at, photo) VALUES ('Alice', 'Smith', 'alicesmith@gmail.com', NOW(), '');
INSERT INTO customers (name, last_name, email, created_at, photo) VALUES ('Tom', 'Jones', 'tomjones@gmail.com', NOW(), '');
INSERT INTO customers (name, last_name, email, created_at, photo) VALUES ('Samantha', 'Jones', 'samanthajones@gmail.com', NOW(), '');
INSERT INTO customers (name, last_name, email, created_at, photo) VALUES ('Michael', 'Johnson', 'michaeljohnson@gmail.com', NOW(), '');
INSERT INTO customers (name, last_name, email, created_at, photo) VALUES ('Emily', 'Johnson', 'emilyjohnson@gmail.com', NOW(), '');

-- Productos
INSERT INTO products (name, price) VALUES ('Panasonic Pantalla LCD', 259990);
INSERT INTO products (name, price) VALUES ('Sony Camara  Digital DSC-W320B', 133490);
INSERT INTO products (name, price) VALUES ('Apple iPod Shuffle', 499990);
INSERT INTO products (name, price) VALUES ('Sony Notebook Z110', 37990);
INSERT INTO products (name, price) VALUES ('Hewlett Packard Multifuncional F2280', 69990);
INSERT INTO products (name, price) VALUES ('Bianchi Bicicleta Aro 26', 69990);
INSERT INTO products (name, price) VALUES ('Mica Comoda 5 Cajones', 299990);
INSERT INTO products (name, price) VALUES ('Silla Gamer Pro', 459990);
INSERT INTO products (name, price) VALUES ('Escritorio Gamer con Luces LED', 699990);
INSERT INTO products (name, price) VALUES ('Cama Individual con Baúl', 329990);

-- Facturas --
-- 1
INSERT INTO invoices (description, observation, customer_id, created_at) VALUES ('Equipos de oficina', 'Debe pagarse antes de la fecha.', 1, NOW());
INSERT INTO invoices_items (quantity, invoice_id, product_id) VALUES (5, 1, 1);
INSERT INTO invoices_items (quantity, invoice_id, product_id) VALUES (5, 1, 2);
INSERT INTO invoices_items (quantity, invoice_id, product_id) VALUES (5, 1, 3);
INSERT INTO invoices_items (quantity, invoice_id, product_id) VALUES (5, 1, 4);

-- 2
INSERT INTO invoices (description, observation, customer_id, created_at) VALUES ('Electrodomésticos', null, 1, NOW());
INSERT INTO invoices_items (quantity, invoice_id, product_id) VALUES (5, 2, 5);
INSERT INTO invoices_items (quantity, invoice_id, product_id) VALUES (5, 2, 6);
INSERT INTO invoices_items (quantity, invoice_id, product_id) VALUES (5, 2, 7);
INSERT INTO invoices_items (quantity, invoice_id, product_id) VALUES (5, 2, 8);

-- Usuarios con sus Roles
INSERT INTO users (username, password, enabled) VALUES ('user' , '$2a$10$wdQiF7li3hIw7YFqzJE.juM/8kCF//0GwLWwZS7DUWuoZTiXnouFm', 1);
INSERT INTO users (username, password, enabled) VALUES ('admin' , '$2a$10$xZ92yjhV1cPFm4tQJ3kQg.8pfGyqHkBm/NjKEwGnVo6B/08lXhsFO', 1);

INSERT INTO authorities (user_id, authority) VALUES (1, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (2, 'ROLE_ADMIN');
INSERT INTO authorities (user_id, authority) VALUES (2, 'ROLE_USER');
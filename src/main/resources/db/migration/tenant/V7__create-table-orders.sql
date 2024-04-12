CREATE TABLE IF NOT EXISTS orders () INHERITS (public.orders);

ALTER TABLE orders ADD CONSTRAINT pk_orders PRIMARY KEY (id);

ALTER TABLE orders ADD CONSTRAINT fk_orders_customer
FOREIGN KEY (customer_id) REFERENCES customer (id);

ALTER TABLE orders ADD CONSTRAINT fk_orders_user
FOREIGN KEY (user_id) REFERENCES users (id);
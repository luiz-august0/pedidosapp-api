CREATE TABLE orders (
    id SERIAL UNIQUE PRIMARY KEY,
    customer_id INT NOT NULL,
    user_id INT NOT NULL,
    amount NUMERIC NOT NULL,
    discount NUMERIC NOT NULL,
    addition NUMERIC NOT NULL,
    status VARCHAR(50) NOT NULL,
    inclusion_date TIMESTAMP NOT NULL
);

ALTER TABLE orders ADD CONSTRAINT fk_orders_customer
FOREIGN KEY (customer_id) REFERENCES customer (id);

ALTER TABLE orders ADD CONSTRAINT fk_orders_user
FOREIGN KEY (user_id) REFERENCES users (id);
CREATE TABLE purchase_order (
    id SERIAL UNIQUE PRIMARY KEY,
    customer_id INT,
    user_id INT NOT NULL,
    order_id INT,
    amount NUMERIC NOT NULL,
    discount NUMERIC NOT NULL,
    addition NUMERIC NOT NULL,
    status VARCHAR(50) NOT NULL,
    inclusion_date TIMESTAMP NOT NULL
);

ALTER TABLE purchase_order ADD CONSTRAINT fk_purchase_order_customer
FOREIGN KEY (customer_id) REFERENCES customer (id);

ALTER TABLE purchase_order ADD CONSTRAINT fk_purchase_order_user
FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE purchase_order ADD CONSTRAINT fk_purchase_order_orders
FOREIGN KEY (order_id) REFERENCES orders (id);
CREATE TABLE order_item (
    id SERIAL UNIQUE PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity NUMERIC NOT NULL,
    unitary_value NUMERIC NOT NULL,
    amount NUMERIC NOT NULL,
    discount NUMERIC NOT NULL,
    addition NUMERIC NOT NULL
);

ALTER TABLE order_item ADD CONSTRAINT fk_order_item_orders
FOREIGN KEY (order_id) REFERENCES orders (id);

ALTER TABLE order_item ADD CONSTRAINT fk_order_item_product
FOREIGN KEY (product_id) REFERENCES product (id);
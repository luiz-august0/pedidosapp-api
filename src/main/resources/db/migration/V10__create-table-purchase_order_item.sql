CREATE TABLE purchase_order_item (
    id SERIAL UNIQUE PRIMARY KEY,
    purchase_order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity NUMERIC NOT NULL,
    unitary_value NUMERIC NOT NULL,
    amount NUMERIC NOT NULL,
    discount NUMERIC NOT NULL,
    addition NUMERIC NOT NULL
);

ALTER TABLE purchase_order_item ADD CONSTRAINT fk_purchase_order_item_purchase_order
FOREIGN KEY (purchase_order_id) REFERENCES purchase_order (id);

ALTER TABLE purchase_order_item ADD CONSTRAINT fk_purchase_order_item_product
FOREIGN KEY (product_id) REFERENCES product (id);
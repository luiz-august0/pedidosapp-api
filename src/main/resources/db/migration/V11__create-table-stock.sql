CREATE TABLE stock (
    id SERIAL UNIQUE PRIMARY KEY,
    purchase_order_id INT,
    order_id INT,
    product_id INT NOT NULL,
    quantity NUMERIC NOT NULL,
    entry BOOLEAN NOT NULL
);

ALTER TABLE stock ADD CONSTRAINT fk_stock_purchase_order
FOREIGN KEY (purchase_order_id) REFERENCES purchase_order (id);

ALTER TABLE stock ADD CONSTRAINT fk_stock_orders
FOREIGN KEY (order_id) REFERENCES orders (id);

ALTER TABLE stock ADD CONSTRAINT fk_stock_product
FOREIGN KEY (product_id) REFERENCES product (id);
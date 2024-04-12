CREATE TABLE IF NOT EXISTS stock () INHERITS (public.stock);

ALTER TABLE stock ADD CONSTRAINT pk_stock PRIMARY KEY (id);

ALTER TABLE stock ADD CONSTRAINT fk_stock_purchase_order
FOREIGN KEY (purchase_order_id) REFERENCES purchase_order (id);

ALTER TABLE stock ADD CONSTRAINT fk_stock_orders
FOREIGN KEY (order_id) REFERENCES orders (id);

ALTER TABLE stock ADD CONSTRAINT fk_stock_product
FOREIGN KEY (product_id) REFERENCES product (id);
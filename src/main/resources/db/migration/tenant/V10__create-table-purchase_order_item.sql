CREATE TABLE IF NOT EXISTS purchase_order_item () INHERITS (public.purchase_order_item);

ALTER TABLE purchase_order_item ADD CONSTRAINT pk_purchase_order_item PRIMARY KEY (id);

ALTER TABLE purchase_order_item ADD CONSTRAINT fk_purchase_order_item_purchase_order
FOREIGN KEY (purchase_order_id) REFERENCES purchase_order (id);

ALTER TABLE purchase_order_item ADD CONSTRAINT fk_purchase_order_item_product
FOREIGN KEY (product_id) REFERENCES product (id);
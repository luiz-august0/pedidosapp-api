CREATE TABLE IF NOT EXISTS order_item () INHERITS (public.order_item);

ALTER TABLE order_item ADD CONSTRAINT pk_order_item PRIMARY KEY (id);

ALTER TABLE order_item ADD CONSTRAINT fk_order_item_orders
FOREIGN KEY (order_id) REFERENCES orders (id);

ALTER TABLE order_item ADD CONSTRAINT fk_order_item_product
FOREIGN KEY (product_id) REFERENCES product (id);

CREATE TRIGGER tr_set_schema_order_item BEFORE INSERT ON order_item FOR EACH ROW EXECUTE PROCEDURE public.trigger_set_schema();
CREATE TABLE IF NOT EXISTS purchase_order () INHERITS (public.purchase_order);

ALTER TABLE purchase_order ADD CONSTRAINT pk_purchase_order PRIMARY KEY (id);

ALTER TABLE purchase_order ADD CONSTRAINT fk_purchase_order_customer
FOREIGN KEY (customer_id) REFERENCES customer (id);

ALTER TABLE purchase_order ADD CONSTRAINT fk_purchase_order_user
FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE purchase_order ADD CONSTRAINT fk_purchase_order_orders
FOREIGN KEY (order_id) REFERENCES orders (id);

CREATE TRIGGER tr_set_schema_purchase_order BEFORE INSERT ON purchase_order FOR EACH ROW EXECUTE PROCEDURE public.trigger_set_schema();
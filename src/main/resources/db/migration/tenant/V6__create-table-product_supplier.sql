CREATE TABLE IF NOT EXISTS product_supplier () INHERITS (public.product_supplier);

ALTER TABLE product_supplier ADD CONSTRAINT pk_product_supplier PRIMARY KEY (id);

ALTER TABLE product_supplier ADD CONSTRAINT fk_product_supplier_product
FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE product_supplier ADD CONSTRAINT fk_product_supplier_supplier
FOREIGN KEY (supplier_id) REFERENCES supplier (id);

CREATE TRIGGER tr_set_schema_product_supplier BEFORE INSERT ON product_supplier FOR EACH ROW EXECUTE PROCEDURE public.trigger_set_schema();
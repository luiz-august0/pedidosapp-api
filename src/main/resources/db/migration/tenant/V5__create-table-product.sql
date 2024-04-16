CREATE TABLE IF NOT EXISTS product () INHERITS (public.product);

ALTER TABLE product ADD CONSTRAINT pk_product PRIMARY KEY (id);

CREATE TRIGGER tr_set_schema_product BEFORE INSERT ON product FOR EACH ROW EXECUTE PROCEDURE public.trigger_set_schema();
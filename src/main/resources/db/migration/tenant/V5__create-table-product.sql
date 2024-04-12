CREATE TABLE IF NOT EXISTS product () INHERITS (public.product);

ALTER TABLE product ADD CONSTRAINT pk_product PRIMARY KEY (id);
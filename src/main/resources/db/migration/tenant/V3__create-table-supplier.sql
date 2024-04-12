CREATE TABLE IF NOT EXISTS supplier () INHERITS (public.supplier);

ALTER TABLE supplier ADD CONSTRAINT pk_supplier PRIMARY KEY (id);
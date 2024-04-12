CREATE TABLE IF NOT EXISTS customer () INHERITS (public.customer);

ALTER TABLE customer ADD CONSTRAINT pk_customer PRIMARY KEY (id);
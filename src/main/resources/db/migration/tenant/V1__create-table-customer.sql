CREATE TABLE IF NOT EXISTS customer () INHERITS (public.customer);

ALTER TABLE customer ADD CONSTRAINT pk_customer PRIMARY KEY (id);

ALTER TABLE customer ADD CONSTRAINT customer_cnpj_key UNIQUE (cnpj);

ALTER TABLE customer ADD CONSTRAINT customer_cpf_key UNIQUE (cpf);

CREATE TRIGGER tr_set_schema_customer BEFORE INSERT ON customer FOR EACH ROW EXECUTE PROCEDURE public.trigger_set_schema();
CREATE TABLE IF NOT EXISTS supplier () INHERITS (public.supplier);

ALTER TABLE supplier ADD CONSTRAINT pk_supplier PRIMARY KEY (id);

ALTER TABLE supplier ADD CONSTRAINT supplier_cnpj_key UNIQUE (cnpj);

ALTER TABLE supplier ADD CONSTRAINT supplier_cpf_key UNIQUE (cpf);

CREATE TRIGGER tr_set_schema_supplier BEFORE INSERT ON supplier FOR EACH ROW EXECUTE PROCEDURE public.trigger_set_schema();
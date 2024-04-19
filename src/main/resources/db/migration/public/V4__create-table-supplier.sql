CREATE TABLE IF NOT EXISTS public.supplier (
    id INT4 NOT NULL,
    name varchar(255) NOT NULL,
    email varchar(255),
    social_reason varchar(255),
    cpf varchar(11),
    cnpj varchar(14),
    contact varchar(20),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    schema varchar(100)
);

CREATE SEQUENCE IF NOT EXISTS public.gen_id_supplier
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

ALTER TABLE public.supplier ADD CONSTRAINT pk_supplier PRIMARY KEY (id);

ALTER TABLE public.supplier ADD CONSTRAINT supplier_cnpj_key UNIQUE (cnpj);

ALTER TABLE public.supplier ADD CONSTRAINT supplier_cpf_key UNIQUE (cpf);

CREATE TRIGGER tr_set_schema_supplier BEFORE INSERT ON supplier FOR EACH ROW EXECUTE PROCEDURE public.trigger_set_schema();
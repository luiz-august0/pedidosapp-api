CREATE TABLE IF NOT EXISTS public.customer (
    id INT4 NOT NULL,
    name varchar(110) NOT NULL,
    email varchar(255),
    cpf varchar(11),
    cnpj varchar(14),
    contact varchar(20),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    schema varchar(100)
);

CREATE SEQUENCE IF NOT EXISTS public.gen_id_customer
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

ALTER TABLE public.customer ADD CONSTRAINT pk_customer PRIMARY KEY (id);

ALTER TABLE public.customer ADD CONSTRAINT customer_cnpj_key UNIQUE (cnpj);

ALTER TABLE public.customer ADD CONSTRAINT customer_cpf_key UNIQUE (cpf);

CREATE TRIGGER tr_set_schema_customer BEFORE INSERT ON customer FOR EACH ROW EXECUTE PROCEDURE public.trigger_set_schema();
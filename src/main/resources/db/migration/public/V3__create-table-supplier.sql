CREATE TABLE IF NOT EXISTS public.supplier (
    id INT4 NOT NULL,
    name varchar(255) NOT NULL,
    email varchar(255),
    social_reason varchar(255),
    cpf varchar(11) UNIQUE,
    cnpj varchar(14) UNIQUE,
    contact varchar(20),
    active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE SEQUENCE IF NOT EXISTS public.gen_id_supplier
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

ALTER TABLE public.supplier ADD CONSTRAINT pk_supplier PRIMARY KEY (id);
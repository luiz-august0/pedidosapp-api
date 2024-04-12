CREATE TABLE IF NOT EXISTS public.product (
    id INT4 NOT NULL,
    description TEXT NOT NULL,
    unit VARCHAR(50) NOT NULL,
    unitary_value NUMERIC NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE SEQUENCE IF NOT EXISTS public.gen_id_product
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

ALTER TABLE public.product ADD CONSTRAINT pk_product PRIMARY KEY (id);
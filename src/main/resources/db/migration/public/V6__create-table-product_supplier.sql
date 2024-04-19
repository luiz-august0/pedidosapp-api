CREATE TABLE IF NOT EXISTS public.product_supplier (
    id INT4 NOT NULL,
    product_id INT NOT NULL,
    supplier_id INT NOT NULL,
    schema varchar(100)
);

CREATE SEQUENCE IF NOT EXISTS public.gen_id_product_supplier
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

ALTER TABLE public.product_supplier ADD CONSTRAINT pk_product_supplier PRIMARY KEY (id);

ALTER TABLE public.product_supplier ADD CONSTRAINT fk_product_supplier_product
FOREIGN KEY (product_id) REFERENCES public.product (id);

ALTER TABLE public.product_supplier ADD CONSTRAINT fk_product_supplier_supplier
FOREIGN KEY (supplier_id) REFERENCES public.supplier (id);

CREATE TRIGGER tr_set_schema_product_supplier BEFORE INSERT ON product_supplier FOR EACH ROW EXECUTE PROCEDURE public.trigger_set_schema();
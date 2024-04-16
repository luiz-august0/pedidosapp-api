CREATE TABLE IF NOT EXISTS public.stock (
    id INT4 NOT NULL,
    purchase_order_id INT,
    order_id INT,
    product_id INT NOT NULL,
    quantity NUMERIC NOT NULL,
    entry BOOLEAN NOT NULL,
    observation VARCHAR(150) NOT NULL,
    schema varchar(100)
);

CREATE SEQUENCE IF NOT EXISTS public.gen_id_stock
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

ALTER TABLE public.stock ADD CONSTRAINT pk_stock PRIMARY KEY (id);

ALTER TABLE public.stock ADD CONSTRAINT fk_stock_purchase_order
FOREIGN KEY (purchase_order_id) REFERENCES public.purchase_order (id);

ALTER TABLE public.stock ADD CONSTRAINT fk_stock_orders
FOREIGN KEY (order_id) REFERENCES public.orders (id);

ALTER TABLE public.stock ADD CONSTRAINT fk_stock_product
FOREIGN KEY (product_id) REFERENCES public.product (id);

CREATE TRIGGER tr_set_schema_stock BEFORE INSERT ON stock FOR EACH ROW EXECUTE PROCEDURE public.trigger_set_schema();
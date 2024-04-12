CREATE TABLE IF NOT EXISTS public.order_item (
    id INT4 NOT NULL,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity NUMERIC NOT NULL,
    unitary_value NUMERIC NOT NULL,
    amount NUMERIC NOT NULL,
    discount NUMERIC NOT NULL,
    addition NUMERIC NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS public.gen_id_order_item
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

ALTER TABLE public.order_item ADD CONSTRAINT pk_order_item PRIMARY KEY (id);

ALTER TABLE public.order_item ADD CONSTRAINT fk_order_item_orders
FOREIGN KEY (order_id) REFERENCES public.orders (id);

ALTER TABLE public.order_item ADD CONSTRAINT fk_order_item_product
FOREIGN KEY (product_id) REFERENCES public.product (id);
CREATE TABLE IF NOT EXISTS public.orders (
    id INT4 NOT NULL,
    customer_id INT NOT NULL,
    user_id INT NOT NULL,
    amount NUMERIC NOT NULL,
    discount NUMERIC NOT NULL,
    addition NUMERIC NOT NULL,
    status VARCHAR(50) NOT NULL,
    inclusion_date TIMESTAMP NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS public.gen_id_order
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

ALTER TABLE public.orders ADD CONSTRAINT pk_orders PRIMARY KEY (id);

ALTER TABLE public.orders ADD CONSTRAINT fk_orders_customer
FOREIGN KEY (customer_id) REFERENCES public.customer (id);

ALTER TABLE public.orders ADD CONSTRAINT fk_orders_user
FOREIGN KEY (user_id) REFERENCES public.users (id);
CREATE TABLE IF NOT EXISTS public.purchase_order (
    id INT4 NOT NULL,
    customer_id INT,
    user_id INT NOT NULL,
    order_id INT,
    amount NUMERIC NOT NULL,
    discount NUMERIC NOT NULL,
    addition NUMERIC NOT NULL,
    status VARCHAR(50) NOT NULL,
    inclusion_date TIMESTAMP NOT NULL,
    schema varchar(100)
);

CREATE SEQUENCE IF NOT EXISTS public.gen_id_purchase_order
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

ALTER TABLE public.purchase_order ADD CONSTRAINT pk_purchase_order PRIMARY KEY (id);

ALTER TABLE public.purchase_order ADD CONSTRAINT fk_purchase_order_customer
FOREIGN KEY (customer_id) REFERENCES public.customer (id);

ALTER TABLE public.purchase_order ADD CONSTRAINT fk_purchase_order_user
FOREIGN KEY (user_id) REFERENCES public.users (id);

ALTER TABLE public.purchase_order ADD CONSTRAINT fk_purchase_order_orders
FOREIGN KEY (order_id) REFERENCES public.orders (id);

CREATE TRIGGER tr_set_schema_purchase_order BEFORE INSERT ON purchase_order FOR EACH ROW EXECUTE PROCEDURE public.trigger_set_schema();
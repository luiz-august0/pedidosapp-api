CREATE TABLE IF NOT EXISTS public.employee (
    id INT4 NOT NULL,
    name varchar(110) NOT NULL,
    email varchar(255),
    cpf varchar(11) UNIQUE,
    contact varchar(20),
    user_id INT NOT NULL UNIQUE,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE SEQUENCE IF NOT EXISTS public.gen_id_employee
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

ALTER TABLE public.employee ADD CONSTRAINT pk_employee PRIMARY KEY (id);

ALTER TABLE public.employee ADD CONSTRAINT fk_employee_user
FOREIGN KEY (user_id) REFERENCES public.users (id);
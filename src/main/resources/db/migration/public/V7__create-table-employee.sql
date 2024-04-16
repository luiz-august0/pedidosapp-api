CREATE TABLE IF NOT EXISTS public.employee (
    id INT4 NOT NULL,
    name varchar(110) NOT NULL,
    email varchar(255),
    cpf varchar(11),
    contact varchar(20),
    user_id INT NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    schema varchar(100)
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

ALTER TABLE public.employee ADD CONSTRAINT employee_cpf_key UNIQUE (cpf);

ALTER TABLE public.employee ADD CONSTRAINT employee_user_id_key UNIQUE (user_id);

CREATE TRIGGER tr_set_schema_employee BEFORE INSERT ON employee FOR EACH ROW EXECUTE PROCEDURE public.trigger_set_schema();

CREATE TABLE IF NOT EXISTS employee () INHERITS (public.employee);

ALTER TABLE employee ADD CONSTRAINT pk_employee PRIMARY KEY (id);

ALTER TABLE employee ADD CONSTRAINT fk_employee_user
FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE employee ADD CONSTRAINT employee_cpf_key UNIQUE (cpf);

ALTER TABLE employee ADD CONSTRAINT employee_user_id_key UNIQUE (user_id);

CREATE TRIGGER tr_set_schema_employee BEFORE INSERT ON employee FOR EACH ROW EXECUTE PROCEDURE public.trigger_set_schema();
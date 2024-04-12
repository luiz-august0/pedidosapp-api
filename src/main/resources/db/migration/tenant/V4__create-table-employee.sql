CREATE TABLE IF NOT EXISTS employee () INHERITS (public.employee);

ALTER TABLE employee ADD CONSTRAINT pk_employee PRIMARY KEY (id);

ALTER TABLE employee ADD CONSTRAINT fk_employee_user
FOREIGN KEY (user_id) REFERENCES users (id);
CREATE TABLE employee (
    id SERIAL UNIQUE PRIMARY KEY,
    name varchar(255) NOT NULL,
    cpf varchar(11) UNIQUE,
    contact varchar(20),
    user_id INT NOT NULL UNIQUE,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

ALTER TABLE employee ADD CONSTRAINT fk_employee_user
FOREIGN KEY (user_id) REFERENCES users (id);
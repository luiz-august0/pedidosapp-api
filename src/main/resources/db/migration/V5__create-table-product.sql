CREATE TABLE product (
    id SERIAL UNIQUE PRIMARY KEY,
    description TEXT NOT NULL,
    unit VARCHAR(50) NOT NULL,
    unitary_value NUMERIC NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE
);
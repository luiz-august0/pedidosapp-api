CREATE TABLE customer (
    id SERIAL UNIQUE PRIMARY KEY,
    name varchar(255) NOT NULL,
    cpf varchar(11) UNIQUE,
    cnpj varchar(14) UNIQUE,
    contact varchar(20),
    active BOOLEAN NOT NULL DEFAULT TRUE
);
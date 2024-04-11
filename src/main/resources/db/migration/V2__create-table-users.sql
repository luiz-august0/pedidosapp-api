CREATE TABLE users (
    id SERIAL UNIQUE PRIMARY KEY,
    login varchar(255) NOT NULL UNIQUE,
    password varchar(255) NOT NULL,
    role varchar(30) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE
);
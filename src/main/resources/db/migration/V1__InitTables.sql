CREATE TABLE users
(
    id       serial primary key,
    login    varchar not null,
    password varchar not null,
    email    varchar not null,
    role     varchar not null
);

CREATE TABLE product
(
    id            serial primary key,
    name          varchar                       not null,
    price         decimal(7, 2),
    image         varchar                       not null,
    images        varchar[],
    description   TEXT,
    quantity      int                           not null default 0,
    accessibility BOOLEAN                                DEFAULT FALSE,
    active        BOOLEAN                                DEFAULT FALSE,
    author        integer REFERENCES users (id) not null,
    width         decimal(10, 2)                not null,
    height        decimal(10, 2)                not null
);

CREATE TABLE "orders"
(
    id          serial primary key,
    name        varchar,
    surname     varchar,
    company     varchar,
    pesel       varchar,
    nip         varchar,
    adress      varchar not null,
    city        varchar not null,
    postCode    varchar not null,
    mail        varchar not null,
    phone       varchar not null,
    description varchar not null,
    isFacture boolean not null DEFAULT false,
    deliver varchar not null,
    cod boolean not null DEFAULT false
);

insert into users(id,login, password, email, role) values (1,'Admin','$2a$10$onNgP2BptZFWK9skmWkwXu2dmJYu8bC6PRlgCSXgazp1P2XNkGV8a','','USER');
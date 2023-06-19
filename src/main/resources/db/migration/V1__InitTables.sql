CREATE TABLE users(
    id serial primary key,
    login varchar not null,
    password varchar not null,
    email varchar not null,
    role varchar not null
);

CREATE TABLE product(
    id serial primary key,
    name varchar not null,
    price decimal(7,2),
    image varchar not null,
    images varchar[],
    description TEXT,
    quantity int not null default 0,
    accessibility BOOLEAN DEFAULT FALSE,
    active BOOLEAN DEFAULT FALSE,
    author integer REFERENCES users(id) not null,
    width decimal(10,2) not null,
    height decimal(10,2) not null
);

CREATE TABLE basket(
    id serial primary key,
    price decimal(7,2) not null,
    priceDeliver decimal(7,2) not null,
    priceFinal decimal(7,2) not null
);

CREATE TABLE basketItems(
    id serial primary key,
    quantity int not null default 1,
    sumPrice decimal(7,2) not null,
    product integer REFERENCES product(id) not null,
    basket integer REFERENCES basket(id) not null
);

CREATE TABLE "order"(
    id serial primary key,
    basket integer REFERENCES basket(id),
    name varchar,
    surname varchar,
    company varchar,
    pesel varchar,
    nip varchar,
    adress varchar not null,
    city varchar not null,
    postCode varchar not null,
    mail varchar not null,
    phone varchar not null,
    description varchar not null
)
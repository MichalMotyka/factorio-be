create table DocumentProduct
(
    id       serial PRIMARY KEY,
    quantity INTEGER,
    document INTEGER,
    product  INTEGER,
    FOREIGN KEY (document) REFERENCES document (id),
    FOREIGN KEY (product) REFERENCES product (id)
)
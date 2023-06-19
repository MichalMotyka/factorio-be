CREATE TABLE Document
(
    id           serial PRIMARY KEY,
    uid          VARCHAR(255) not null,
    createDate   DATE         not null,
    endDate      DATE,
    author       integer,
    description  VARCHAR(255),
    status       VARCHAR,
    documentType varchar,
    FOREIGN KEY (author) REFERENCES users (id)
);

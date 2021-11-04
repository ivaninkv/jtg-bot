DROP TABLE IF EXISTS tg_user;

CREATE TABLE tg_user
(
    id   BIGINT PRIMARY KEY,
    name VARCHAR(50),
    active BOOLEAN
);

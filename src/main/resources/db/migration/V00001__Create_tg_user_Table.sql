DROP TABLE IF EXISTS tg_user;

CREATE TABLE tg_user
(
    chat_id BIGINT PRIMARY KEY,
    name    VARCHAR(50),
    active  BOOLEAN
);

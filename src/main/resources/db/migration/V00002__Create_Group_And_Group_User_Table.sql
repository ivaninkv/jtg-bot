DROP TABLE IF EXISTS group_sub;
DROP TABLE IF EXISTS group_x_user;

CREATE TABLE group_sub
(
    id              INT PRIMARY KEY,
    title           VARCHAR(100),
    last_article_id INT
);

CREATE TABLE group_x_user
(
    group_sub_id INT    NOT NULL REFERENCES group_sub,
    user_id      BIGINT NOT NULL REFERENCES tg_user,
    UNIQUE (user_id, group_sub_id)
);

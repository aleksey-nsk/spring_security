CREATE TABLE my_user
(
    id        bigserial PRIMARY KEY,
    username  varchar,
    password  varchar,
    position  varchar,
--     role     varchar
    authority varchar
);

-- INSERT INTO my_user(username, password, position, role)
INSERT INTO my_user(username, password, position, authority)
VALUES ('user', 'p1', 'Финансовый Советник', 'ROLE_USER'),
       ('admin', 'p2', 'Начальник Отдела', 'ROLE_ADMIN');

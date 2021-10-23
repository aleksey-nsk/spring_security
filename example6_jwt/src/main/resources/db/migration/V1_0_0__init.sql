CREATE TABLE my_user
(
    id       bigserial PRIMARY KEY,
    username varchar,
    password varchar,
    position varchar,
    role     varchar
);

INSERT INTO my_user(username, password, position, role)
VALUES ('user', 'u', 'Финансовый Советник', 'USER'),
       ('admin', 'a', 'Начальник Отдела', 'ADMIN');

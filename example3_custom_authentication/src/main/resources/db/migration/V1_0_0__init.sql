CREATE TABLE my_user
(
    id       bigserial PRIMARY KEY,
    username varchar,
    password varchar,
    position varchar,
    role     varchar
);

INSERT INTO my_user(username, password, position, role)
VALUES ('user', 'p1', 'Финансовый Советник', 'USER'),
       ('admin', 'p2', 'Начальник Отдела', 'ADMIN');

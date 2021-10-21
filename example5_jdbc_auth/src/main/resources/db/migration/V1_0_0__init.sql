CREATE TABLE my_user
(
    id        bigserial PRIMARY KEY,
    username  varchar,
    password  varchar,
    position  varchar,
    authority varchar
);

INSERT INTO my_user(username, password, position, authority)
VALUES ('user1', 'u1', 'Финансовый Советник', 'ROLE_USER'),
       ('user2', 'u2', 'Оператор Call-центра', 'ROLE_USER'),
       ('admin', 'a1', 'Начальник Отдела', 'ROLE_ADMIN');

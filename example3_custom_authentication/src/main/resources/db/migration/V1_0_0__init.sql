CREATE TABLE my_user
(
    id       bigserial PRIMARY KEY,
    username varchar,
    password varchar,
    position varchar,
    role     varchar
);

INSERT INTO my_user(username, password, position, role)
VALUES ('user', 'p1', 'position1', 'USER'),
       ('admin', 'p2', 'position2', 'ADMIN');

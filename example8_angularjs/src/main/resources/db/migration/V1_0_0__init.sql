CREATE TABLE products
(
    id                bigserial PRIMARY KEY,
    title             varchar,
    price             int,
    created_date_time date,
    updated_date_time date
);

INSERT INTO products(title, price)
VALUES ('Пылесос', 100),
       ('Ноутбук', 200),
       ('Обувь', 123),
       ('Potato', 23),
       ('Tomato', 35),
       ('Milk', 62),
       ('Water', 44);

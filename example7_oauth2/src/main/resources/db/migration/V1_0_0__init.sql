CREATE TABLE my_user
(
    id      varchar PRIMARY KEY,
    name    varchar,
    userpic varchar,
    email   varchar,
    gender  varchar,
    locale  varchar
);

INSERT INTO my_user(id, name, userpic, email, gender, locale)
VALUES ('1', 'user', null, 'user@test_email.ru', 'male', null);

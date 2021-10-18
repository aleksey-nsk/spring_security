-- Заполним БД парой пользователей с помощью data.sql (этот файл надо положить в папку resources)
INSERT INTO my_user(username, password, position, role)
VALUES ('user', 'pswd1', 'position1', 'USER'),
       ('admin', 'pswd2', 'position2', 'ADMIN');

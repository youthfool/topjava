DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (date_time, user_id, description, calories)
VALUES ('2020-01-30 10:00', 100000, 'Breakfast', 500),
       ('2020-01-30 13:00', 100000, 'Lunch', 1000),
       ('2020-01-30 20:00', 100000, 'Supper', 500),
       ('2020-01-31 00:00', 100001, 'Bordering', 100),
       ('2020-01-31 10:00', 100001, 'Breakfast', 1000),
       ('2020-01-31 13:00', 100001, 'Lunch', 500),
       ('2020-01-31 20:00', 100001, 'Supper', 410);

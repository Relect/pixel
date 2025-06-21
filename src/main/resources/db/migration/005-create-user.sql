--liquibase formatted sql
--changeset Gennadiy:004
--preconditions onFail:HALT onError:HALT

INSERT INTO "user" (name, date_of_birth, password)
VALUES ('Ivan', '1990-01-31', '$2a$10$.1AoLkbN9iIAOdH6GECwyeTlzVJLhQMxzCSGHANC/nNummwTKnrYa');

INSERT INTO account (user_id, balance)
VALUES (1, 100.00);

INSERT INTO email_data (user_id, email)
VALUES (1, 'mail@example.com');

INSERT INTO phone_data (user_id, phone)
VALUES (1, '79201234567');
--liquibase formatted sql
--changeset Gennadiy:007
--preconditions onFail:HALT onError:HALT

INSERT INTO "user" (name, date_of_birth, password)
VALUES ('Misha', '1990-01-31', '$2a$10$.1AoLkbN9iIAOdH6GECwyeTlzVJLhQMxzCSGHANC/nNummwTKnrYa');

INSERT INTO account (user_id, balance, start_balance)
VALUES (2, 140.00, 140.00);

INSERT INTO email_data (user_id, email)
VALUES (2, 'email1@example2.com');

INSERT INTO phone_data (user_id, phone)
VALUES (2, '79201234568');
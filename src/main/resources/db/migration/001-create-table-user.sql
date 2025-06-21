--liquibase formatted sql
--changeset Gennadiy:001
--preconditions onFail:HALT onError:HALT

CREATE TABLE IF NOT EXISTS "user" (
    id bigserial PRIMARY KEY,
    name varchar(500) NOT NULL,
    date_of_birth date NOT NULL,
    password varchar(500) NOT NULL CHECK (length(password) >= 8));
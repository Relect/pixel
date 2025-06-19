--liquibase formatted sql
--changeset Gennadiy:001
--preconditions onFail:HALT onError:HALT

CREATE TABLE IF NOT EXISTS "user" (
    id bigserial PRIMARY KEY,
    name varchar(500),
    date_of_birth date,
    password varchar(500));






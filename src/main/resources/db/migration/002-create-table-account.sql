--liquibase formatted sql
--changeset Gennadiy:002
--preconditions onFail:HALT onError:HALT

CREATE TABLE IF NOT EXISTS account (
    id bigserial PRIMARY KEY,
    user_id bigint unique,
    balance decimal,
    FOREIGN KEY (user_id) References "user" (id));
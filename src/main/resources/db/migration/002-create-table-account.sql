--liquibase formatted sql
--changeset Gennadiy:002
--preconditions onFail:HALT onError:HALT

CREATE TABLE IF NOT EXISTS account (
    id bigserial PRIMARY KEY,
    user_id bigint NOT NULL unique,
    balance decimal NOT NULL CHECK (balance >= 0),
    FOREIGN KEY (user_id) References "user" (id) ON DELETE CASCADE);
--liquibase formatted sql
--changeset Gennadiy:003
--preconditions onFail:HALT onError:HALT

CREATE TABLE IF NOT EXISTS email_data (
    id bigserial PRIMARY KEY,
    user_id bigint,
    email varchar(200) unique,
    FOREIGN KEY (user_id) References "user" (id));
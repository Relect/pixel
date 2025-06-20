--liquibase formatted sql
--changeset Gennadiy:003
--preconditions onFail:HALT onError:HALT

CREATE TABLE IF NOT EXISTS email_data (
    id bigserial PRIMARY KEY,
    user_id bigint NOT NULL,
    email varchar(200) NOT NULL unique,
    FOREIGN KEY (user_id) References "user" (id) ON DELETE CASCADE);
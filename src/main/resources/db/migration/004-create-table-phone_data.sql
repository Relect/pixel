--liquibase formatted sql
--changeset Gennadiy:004
--preconditions onFail:HALT onError:HALT

CREATE TABLE IF NOT EXISTS phone_data (
    id bigserial PRIMARY KEY,
    user_id bigint NOT NULL,
    phone varchar(13) NOT NULL unique,
    FOREIGN KEY (user_id) References "user" (id) ON DELETE CASCADE);
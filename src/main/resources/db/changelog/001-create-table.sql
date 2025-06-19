--liquibase formatted sql
--changeset Gennadiy:001
--preconditions onFail:HALT onError:HALT

CREATE TABLE IF NOT EXISTS user (
    id bigserial PRIMARY KEY,
    name varchar(500),
    date_of_birth date,
    password varchar(500));

CREATE TABLE IF NOT EXISTS account (
    id bigserial PRIMARY KEY,
    user_id bigint unique,
    balance decimal,
    FOREIGN KEY (user_id) References user (id));

CREATE TABLE IF NOT EXISTS email_data (
    id bigserial PRIMARY KEY,
    user_id bigint,
    email varchar(200) unique,
    FOREIGN KEY (user_id) References user (id));

CREATE TABLE IF NOT EXISTS phone_data (
    id bigserial PRIMARY KEY,
    user_id bigint,
    phone varchar(13) unique,
    FOREIGN KEY (user_id) References user (id));
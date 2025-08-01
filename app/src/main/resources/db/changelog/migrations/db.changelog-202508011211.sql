--liquibase formatted sql
--changeset diogo:202508011211
--comment: boards table create

CREATE TABLE BOARDS(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

--rollback DROP TABLE BOARDS

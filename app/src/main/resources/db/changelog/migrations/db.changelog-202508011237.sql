--liquibase formatted sql
--changeset diogo:202508011211
--comment: cards table create

CREATE TABLE CARDS(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    "description" VARCHAR(255) NOT NULL,
    "order" INT NOT NULL,
    board_column_id BIGINT NOT NULL,
    CONSTRAINT boards_columns__cards_fk FOREIGN KEY (board_column_id) REFERENCES BOARDS_COLUMNS(id) ON DELETE CASCADE
);

--rollback DROP TABLE CARDS

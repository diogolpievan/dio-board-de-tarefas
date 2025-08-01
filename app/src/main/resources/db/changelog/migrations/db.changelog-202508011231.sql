--liquibase formatted sql
--changeset diogo:202508011211
--comment: boards_column table create

CREATE TABLE BOARDS_COLUMNS(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    "order" INT NOT NULL,
    kind VARCHAR(7) NOT NULL,
    board_id BIGINT NOT NULL,
    CONSTRAINT boards__boards_columns_fk FOREIGN KEY (board_id) REFERENCES BOARDS(id) ON DELETE CASCADE,
    CONSTRAINT id_order_uk UNIQUE (board_id, "order") 
);

--rollback DROP TABLE BOARDS_COLUMNS

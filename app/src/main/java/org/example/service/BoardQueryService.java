package org.example.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import org.example.persistence.dao.BoardColumnDAO;
import org.example.persistence.dao.BoardDAO;
import org.example.persistence.entity.BoardEntity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoardQueryService {
    private final Connection connection;

    public Optional<BoardEntity> findById(final Long id) throws SQLException {
        BoardDAO dao = new BoardDAO(connection);
        BoardColumnDAO boardColumnDAO = new BoardColumnDAO(connection);
        Optional<BoardEntity> optional = dao.findById(id);

        if (optional.isPresent()) {
            BoardEntity entity = optional.get();
            entity.setBoardColumns(boardColumnDAO.findByBoardId(id));
            return Optional.of(entity);
        }
        return Optional.empty();
    }
}

package org.example.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.example.persistence.entity.BoardColumnEntity;
import org.example.persistence.entity.BoardColumnKindEnum;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardColumnDAO {
    
    private final Connection connection;

    public BoardColumnEntity insert(final BoardColumnEntity entity) throws SQLException{
        String sql = "INSERT INTO boards_columns (name, order, kind, board_id) VALUES (?, ?, ?, ?);";
        try(PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getOrder());
            statement.setString(3, entity.getKind().name());
            statement.setLong(4, entity.getBoard().getId());
            statement.executeUpdate();

            try(var generatedKeys = statement.getGeneratedKeys()){
                if(generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }
        }
        return entity;
    }

    public List<BoardColumnEntity> findByBoardId(final Long boardId) throws SQLException{
        List<BoardColumnEntity> entitiesFound = new ArrayList<>();

        String sql = "SELECT * FROM boards_columns WHERE board_id = ? ORDER BY \"order\"";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, boardId);
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();

            while(resultSet.next()){
                BoardColumnEntity entity = new BoardColumnEntity();
                entity.setId(resultSet.getLong("id"));
                entity.setName(resultSet.getString("name"));
                entity.setOrder(resultSet.getInt("order"));
                entity.setKind(BoardColumnKindEnum.findByName(resultSet.getString("kind")));

                entitiesFound.add(entity);
            }
        }
        return entitiesFound;
    }
    
}

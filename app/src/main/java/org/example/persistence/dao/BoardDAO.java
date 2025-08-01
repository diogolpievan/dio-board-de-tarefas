package org.example.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import org.example.persistence.entity.BoardEntity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoardDAO {
    
    private final Connection connection;
    
    private BoardEntity insert(final BoardEntity entity) throws SQLException {
        String sql = "INSERT INTO boards (name) VALUES (?);";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, entity.getName());
            statement.executeUpdate();

            try(var generatedKeys = statement.getGeneratedKeys()){
                if (generatedKeys.next()){
                    entity.setId(generatedKeys.getLong(1));
                }
            }
        }
        return entity;
    }

    private void delete(final Long id) throws SQLException {    
        String sql = "DELETE FROM boards WHERE id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)){ 
            statement.setLong(1, id);
            statement.executeUpdate();

        }
        
    }

    private Optional<BoardEntity> findById(final Long id) throws SQLException {
        String sql = "SELECT * from boards WHERE id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)){ 
            statement.setLong(1, id);
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();

            if(resultSet.next()){
                BoardEntity boardEntityFound = new BoardEntity();
                boardEntityFound.setId(id);
                boardEntityFound.setName(resultSet.getString("name"));
                return Optional.of(boardEntityFound);
            }
            return Optional.empty();
        }
    }

    private boolean exists(final Long id) throws SQLException {
        String sql = "SELECT 1 FROM boards WHERE id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);
            statement.executeQuery();
            return statement.getResultSet().next();
        }
    }
}

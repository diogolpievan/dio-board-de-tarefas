package org.example.persistence.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ConnectionConfig {

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/board_de_tarefas";
        String user = "postgres";
        String password = "postgres";

        Connection connection = DriverManager.getConnection(url, user, password);
        connection.setAutoCommit(false);
        return connection;        
    }
}

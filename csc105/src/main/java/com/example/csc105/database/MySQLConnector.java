package com.example.csc105.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector {
    public static final String URL = "jdbc:mysql://csproject.sit.kmutt.ac.th:3306/db63130500221";
    public static final String USERNAME = "63130500221";
    public static final String PASSWORD = "abcd1234";
    private static Connection connection;

    public MySQLConnector() throws SQLException {
        reconnect();

    }
    public static Connection getConnection() throws SQLException {
        if (connection.isClosed())
            reconnect();
        return connection;
    }
    private static void reconnect() throws SQLException {
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}

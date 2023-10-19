package com.systechafrica.db.impl;

import com.systechafrica.db.DatabaseHandler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHandlerMySqlImpl implements DatabaseHandler {
    @Override
    public Connection connect(String connectionUrl, String username, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(connectionUrl, username, password);
    }
}

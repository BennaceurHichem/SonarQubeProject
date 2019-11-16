package com.example.service;



import com.example.main.Home;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import java.util.logging.*;
public class DatabaseConnection {
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());

    private String user;
    private String pass;
    private String jdbcDriver;
    private String dbUrl;

    public DatabaseConnection(String user, String jdbcDriver, String dbUrl) throws IOException {
        this.user = user;
        this.jdbcDriver = jdbcDriver;
        this.dbUrl = dbUrl;
        this.pass =Home.getPassFromFile();
    }

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbUrl, user, pass);
            Class.forName(jdbcDriver);
        } catch (SQLException e) {
            LOGGER.log(Level.INFO, "My first Log Message");
        } catch (ClassNotFoundException e) {
            LOGGER.warning(e.getMessage());
        }
        return conn;
    }

    public void disconnect(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
        }
    }

    public void createDb(Connection conn) {

        try(Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE   City " +
                    "(idCity INTEGER not NULL, " +
                    " name VARCHAR(255), " +
                    " touristNumber INTEGER, " +
                    " description VARCHAR(255), " +
                    " PRIMARY KEY ( idCity ))";
            stmt.executeUpdate(sql);

        }  catch (Exception e) {
            LOGGER.warning(e.getMessage());

        }
    }


}

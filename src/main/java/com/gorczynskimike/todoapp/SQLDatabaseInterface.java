package com.gorczynskimike.todoapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SQLDatabaseInterface implements DatabaseInterface{

    Connection conn = null;
    Statement stmt = null;
    String databaseName = "test";

    @Override
    public List<Task> loadUserTasks(User user) {
        return null;
    }

    @Override
    public boolean saveUserTasks(User user, List<Task> userTasks) {
        return false;
    }

    @Override
    public boolean userExists(String userName) {
        return false;
    }

    @Override
    public User createUser(String userName) {
        return null;
    }

    @Override
    public User loadUser(String userName) {
        return null;
    }

    @Override
    public void initialize() {

        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:./" + databaseName, "", "");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void cleanup() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getDatabaseName() {
        return this.databaseName;
    }
}

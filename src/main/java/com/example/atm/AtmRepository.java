

package com.example.atm;

import java.sql.*;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AtmRepository {

    private static final String DB_URL = "jdbc:sqlite:atm_session_data.db";
    private static final String TABLE_NAME = "session_data";
    private static final String COLUMN_KEY = "key";
    private static final String COLUMN_VALUE = "value";

    private Connection connection;
    private Lock lock = new ReentrantLock();

    public AtmRepository() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            createTable();
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
        }
    }

    private void createTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_KEY + " TEXT PRIMARY KEY, " +
                COLUMN_VALUE + " TEXT)";
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        }
    }

    public void saveSessionData(Map<String, String> sessionData) {
        lock.lock();
        try {
            for (Map.Entry<String, String> entry : sessionData.entrySet()) {
                String query = "INSERT OR REPLACE INTO " + TABLE_NAME + " (" +
                        COLUMN_KEY + ", " + COLUMN_VALUE + ") VALUES (?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, entry.getKey());
                    statement.setString(2, entry.getValue());
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saving session data: " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    public Map<String, String> loadSessionData() {
        Map<String, String> sessionData = new HashMap<>();
        lock.lock();
        try {
            String query = "SELECT * FROM " + TABLE_NAME;
            try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    sessionData.put(resultSet.getString(COLUMN_KEY), resultSet.getString(COLUMN_VALUE));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading session data: " + e.getMessage());
        } finally {
            lock.unlock();
        }
        return sessionData;
    }
}
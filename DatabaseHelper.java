package com.optihealth;

import java.sql.*;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:optihealth.db";

    static {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            stmt.execute("PRAGMA foreign_keys = ON;");

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    email TEXT NOT NULL UNIQUE,
                    password_hash TEXT NOT NULL,
                    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS user_profiles (
                    user_id INTEGER PRIMARY KEY,
                    full_name TEXT,
                    age INTEGER,
                    gender TEXT,
                    height_cm REAL,
                    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS bmi_log (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_id INTEGER,
                    height_cm REAL,
                    weight_kg REAL,
                    bmi REAL,
                    category TEXT,
                    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS goals (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_id INTEGER NOT NULL,
                    target_weight_kg REAL,
                    target_body_part TEXT,
                    target_weeks INTEGER,
                    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS diet_plans (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    bmi_category TEXT NOT NULL,
                    title TEXT NOT NULL,
                    description TEXT NOT NULL
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS workout_plans (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    body_part TEXT NOT NULL,
                    intensity TEXT,
                    title TEXT NOT NULL,
                    description TEXT NOT NULL
                )
            """);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- basic operations ---

    public static int registerUser(String email, String passwordHash) throws SQLException {
        String sql = "INSERT INTO users (email, password_hash) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, email);
            ps.setString(2, passwordHash);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    public static void saveBmiLog(Integer userId, double heightCm, double weightKg,
                                  double bmi, String category) throws SQLException {
        String sql = "INSERT INTO bmi_log (user_id, height_cm, weight_kg, bmi, category) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (userId == null) ps.setNull(1, Types.INTEGER);
            else ps.setInt(1, userId);
            ps.setDouble(2, heightCm);
            ps.setDouble(3, weightKg);
            ps.setDouble(4, bmi);
            ps.setString(5, category);
            ps.executeUpdate();
        }
    }

    public static void saveGoal(int userId, double targetWeightKg,
                                String targetBodyPart, int targetWeeks) throws SQLException {
        String sql = "INSERT INTO goals (user_id, target_weight_kg, target_body_part, target_weeks) " +
                     "VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setDouble(2, targetWeightKg);
            ps.setString(3, targetBodyPart);
            ps.setInt(4, targetWeeks);
            ps.executeUpdate();
        }
    }
}
/*
 * Database Connection Class
 * Connects to Oracle 23ai Free database
 */
package studentcomplaintmanagementsystem.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    // Database configuration
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521/FREE";
    private static final String DB_USER = "C##TONY17";
    private static final String DB_PASSWORD = "7897";
    
    static {
        try {
            // Load Oracle JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("Oracle JDBC Driver not found!");
            e.printStackTrace();
        }
    }
    
    /**
     * Get database connection
     * @return Connection object
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
    
    /**
     * Test database connection
     * @return true if connection successful
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            System.out.println("Database connected successfully!");
            return true;
        } catch (SQLException e) {
            System.err.println("Database connection failed!");
            e.printStackTrace();
            return false;
        }
    }
}

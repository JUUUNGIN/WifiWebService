package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseUtil {
	
	private static final String URL = "jdbc:mysql://localhost:3306/wifi_data"; // yourDatabaseName을 실제 데이터베이스 이름으로 변경
    private static final String USER = "root"; // yourUsername을 실제 사용자 이름으로 변경
    private static final String PASSWORD = "9999"; // yourPassword를 실제 비밀번호로 변경

    public static Connection getConnection() throws SQLException {
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("MySQL JDBC Driver not found.");
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                System.out.println("Database connected!");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



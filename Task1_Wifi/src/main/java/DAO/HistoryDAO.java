package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DTO.History;

public class HistoryDAO {

    private Connection getConnection() throws SQLException {
        String dbURL = "jdbc:mysql://localhost:3306/wifi_data";
        String dbUser = "root";
        String dbPassword = "9999";
        return DriverManager.getConnection(dbURL, dbUser, dbPassword);
    }

    public void saveHistory(float latitude, float longitude, Timestamp timestamp) {
        String query = "INSERT INTO history (latitude, longitude, timestamp) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setFloat(1, latitude);
            ps.setFloat(2, longitude);
            ps.setTimestamp(3, timestamp);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<History> getHistory() {
        List<History> historyList = new ArrayList<>();
        String query = "SELECT * FROM history ORDER BY timestamp DESC";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                float latitude = rs.getFloat("latitude");
                float longitude = rs.getFloat("longitude");
                Timestamp timestamp = rs.getTimestamp("timestamp");
                historyList.add(new History(id, latitude, longitude, timestamp));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historyList;
    }

    public void deleteHistory(int id) {
        String query = "DELETE FROM history WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

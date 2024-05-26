package com.example;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnquiryDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/school_db";
    private static final String USER = "root";
    private static final String PASSWORD = "root@123"; 

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void addEnquiry(Enquiry enquiry) {
        String query = "INSERT INTO enquiries (parent_username, message) VALUES (?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, enquiry.getParentUsername());
            pstmt.setString(2, enquiry.getMessage());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Enquiry> getAllEnquiries() {
        List<Enquiry> enquiries = new ArrayList<>();
        String query = "SELECT * FROM enquiries";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Enquiry enquiry = new Enquiry();
                enquiry.setId(rs.getInt("id"));
                enquiry.setParentUsername(rs.getString("parent_username"));
                enquiry.setMessage(rs.getString("message"));
                enquiry.setSubmittedAt(rs.getTimestamp("submitted_at"));
                enquiries.add(enquiry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enquiries;
    }
}

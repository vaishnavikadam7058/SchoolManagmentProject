package com.example;

import java.sql.*;
import java.util.*;

public class AttendanceDAO {
    private Connection connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/school_db";
        String user = "root";
        String password = "root@123";
        return DriverManager.getConnection(url, user, password);
    }

    public List<AttendanceRecord> getAllAttendanceRecords() {
        List<AttendanceRecord> records = new ArrayList<>();
        String query = "SELECT * FROM attendance";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                AttendanceRecord record = new AttendanceRecord(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("standard"),
                    rs.getString("attendance_status"),
                    rs.getDate("date")
                );
                records.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    public boolean addAttendanceRecord(AttendanceRecord record) {
        String query = "INSERT INTO attendance (name, standard, attendance_status, date) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, record.getName());
            stmt.setString(2, record.getStandard());
            stmt.setString(3, record.getAttendanceStatus());
            stmt.setDate(4, new java.sql.Date(record.getDate().getTime()));
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAttendanceRecord(AttendanceRecord record) {
        String query = "UPDATE attendance SET name = ?, standard = ?, attendance_status = ?, date = ? WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, record.getName());
            stmt.setString(2, record.getStandard());
            stmt.setString(3, record.getAttendanceStatus());
            stmt.setDate(4, new java.sql.Date(record.getDate().getTime()));
            stmt.setInt(5, record.getId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAttendanceRecord(int id) {
        String query = "DELETE FROM attendance WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

	
	public List<String> getAllStudentQueries() {
	        List<String> queries = new ArrayList<>();
	        String query = "SELECT query FROM queries";
	        try (Connection conn = connect();
	             Statement stmt = conn.createStatement();
	             ResultSet rs = stmt.executeQuery(query)) {
	            while (rs.next()) {
	                String queryText = rs.getString("query");
	                queries.add(queryText);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return queries;
	    }
	}



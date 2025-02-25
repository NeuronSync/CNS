package com.university.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnalyticsDAO {

    // Get total enrollments per course
    public List<String[]> getEnrollmentAnalytics() {
        List<String[]> results = new ArrayList<>();
        String sql = "SELECT c.course_name, COUNT(e.student_id) AS total_enrollments " +
                     "FROM enrollments e " +
                     "JOIN courses c ON e.course_id = c.course_id " +
                     "GROUP BY c.course_name";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                results.add(new String[]{rs.getString("course_name"), String.valueOf(rs.getInt("total_enrollments"))});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    // Get grade distribution
    public List<String[]> getGradeDistribution() {
        List<String[]> results = new ArrayList<>();
        String sql = "SELECT " +
                     "CASE " +
                     "WHEN grade >= 75 THEN 'A' " +
                     "WHEN grade >= 60 THEN 'B' " +
                     "WHEN grade >= 50 THEN 'C' " +
                     "WHEN grade >= 40 THEN 'D' " +
                     "ELSE 'F' " +
                     "END AS grade_category, COUNT(*) AS student_count " +
                     "FROM enrollments " +
                     "WHERE grade IS NOT NULL " +
                     "GROUP BY grade_category";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                results.add(new String[]{rs.getString("grade_category"), String.valueOf(rs.getInt("student_count"))});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    // Get average grade per course
    public List<String[]> getAverageGrades() {
        List<String[]> results = new ArrayList<>();
        String sql = "SELECT c.course_name, AVG(e.grade) AS average_grade " +
                     "FROM enrollments e " +
                     "JOIN courses c ON e.course_id = c.course_id " +
                     "WHERE e.grade IS NOT NULL " +
                     "GROUP BY c.course_name";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                results.add(new String[]{rs.getString("course_name"), String.format("%.2f", rs.getDouble("average_grade"))});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
}

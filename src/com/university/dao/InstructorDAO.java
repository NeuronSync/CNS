package com.university.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.university.models.Course;
import com.university.auth.BCryptUtil;
import com.university.dao.DatabaseConnector;

public class InstructorDAO {

    // Update instructor profile
    public boolean updateProfile(int instructorId, String name, String email) {
        String sql = "UPDATE instructors SET name = ?, email = ? WHERE instructor_id = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setInt(3, instructorId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Change instructor password
    public boolean changePassword(int instructorId, String newPassword) {
        String hashedPassword = BCryptUtil.hashPassword(newPassword); // Ensure BCryptUtil exists
        String sql = "UPDATE instructors SET password_hash = ? WHERE instructor_id = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, hashedPassword);
            stmt.setInt(2, instructorId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Course> getAssignedCourses(int instructorId) {
    List<Course> courses = new ArrayList<>();
    String sql = """
        SELECT c.course_id, c.course_name, c.course_code, c.department, c.semester, c.credits
        FROM courses AS c
        JOIN instructor_courses AS ic ON c.course_id = ic.course_id
        WHERE ic.instructor_id = ?
    """;

    try (Connection conn = DatabaseConnector.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, instructorId);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                courses.add(new Course(
                    rs.getInt("course_id"),
                    rs.getString("course_name"),
                    rs.getString("course_code"),
                    rs.getString("department"),
                    rs.getString("semester"),
                    rs.getInt("credits")
                ));
            }
        }
    } catch (SQLException e) {
        System.err.println("Error fetching assigned courses for instructor ID: " + instructorId);
        e.printStackTrace();
    }
    return courses;
}

}

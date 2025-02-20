package com.university.dao;

import com.university.models.Course;
import com.university.dao.DatabaseConnector;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    // Add a new course
    public boolean addCourse(String courseName, String courseCode, String department, String semester, int credits) {
        String sql = "INSERT INTO courses (course_name, course_code, department, semester, credits) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, courseName);
            stmt.setString(2, courseCode);
            stmt.setString(3, department);
            stmt.setString(4, semester);
            stmt.setInt(5, credits);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update course details
    public boolean updateCourse(int courseId, String courseName, String courseCode, String department, String semester, int credits) {
        String sql = "UPDATE courses SET course_name = ?, course_code = ?, department = ?, semester = ?, credits = ? WHERE course_id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, courseName);
            stmt.setString(2, courseCode);
            stmt.setString(3, department);
            stmt.setString(4, semester);
            stmt.setInt(5, credits);
            stmt.setInt(6, courseId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete a course
    public boolean deleteCourse(int courseId) {
        String sql = "DELETE FROM courses WHERE course_id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, courseId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get all courses
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
}

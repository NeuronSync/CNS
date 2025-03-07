package com.university.dao;

import com.university.models.Enrollment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO {

    // Enroll a student in a course
    public boolean enrollStudent(int studentId, int courseId) {
        String sql = "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update student enrollment (e.g., change course)
    public boolean updateEnrollment(int enrollmentId, int newCourseId) {
        String sql = "UPDATE enrollments SET course_id = ? WHERE enrollment_id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newCourseId);
            stmt.setInt(2, enrollmentId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Unenroll a student from a course
    public boolean deleteEnrollment(int enrollmentId) {
        String sql = "DELETE FROM enrollments WHERE enrollment_id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, enrollmentId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get all enrollments
    public List<Enrollment> getAllEnrollments() {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT e.enrollment_id, u.email, c.course_name, e.enrollment_date, e.grade " +
                     "FROM enrollments e " +
                     "JOIN users u ON e.student_id = u.user_id " +
                     "JOIN courses c ON e.course_id = c.course_id";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                enrollments.add(new Enrollment(
                    rs.getInt("enrollment_id"),
                    rs.getString("email"),
                    rs.getString("course_name"),
                    rs.getTimestamp("enrollment_date"),
                    rs.getBigDecimal("grade")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enrollments;
    }

    public List<String> getEnrolledCoursesByStudentId(int studentId) {
    List<String> courses = new ArrayList<>();
    String sql = "SELECT c.course_name, c.course_code, c.semester " +
                 "FROM enrollments e " +
                 "JOIN courses c ON e.course_id = c.course_id " +
                 "WHERE e.student_id = ?";

    try (Connection conn = DatabaseConnector.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, studentId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String courseName = rs.getString("course_name");
            String courseCode = rs.getString("course_code");
            String semester = rs.getString("semester");
            courses.add(courseName + " (" + courseCode + ") - " + semester);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return courses;
}

public List<String> getStudentGrades(int studentId) {
    List<String> grades = new ArrayList<>();
    String sql = "SELECT c.course_name, c.course_code, e.grade " +
                 "FROM enrollments e " +
                 "JOIN courses c ON e.course_id = c.course_id " +
                 "WHERE e.student_id = ?";

    try (Connection conn = DatabaseConnector.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, studentId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String courseName = rs.getString("course_name");
            String courseCode = rs.getString("course_code");
            String grade = (rs.getBigDecimal("grade") != null) ? rs.getBigDecimal("grade").toString() : "N/A";
            grades.add(courseName + " (" + courseCode + ") - Grade: " + grade);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return grades;
}
}

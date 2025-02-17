package com.university.database.DAO;

import java.util.List;
import java.util.ArrayList;
import com.university.database.DatabaseConnector;
import com.university.model.Student;
import com.university.model.Course;
import com.university.model.Enrollment;
import java.sql.*;

public class EnrollmentDAO {
    
    // Method to enroll a student in a course
    public void enrollStudent(Student student, Course course) throws SQLException {
        String sql = "INSERT INTO Enrollment (student_id, course_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, student.getPersonId()); // Assume Student has getPersonId()
            stmt.setInt(2, course.getCourseId());
            stmt.executeUpdate();
        }
    }

    // Method to get all enrollments without grades
    public List<Enrollment> getEnrollmentsWithoutGrades() throws SQLException {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT e.enrollment_id, s.name AS student_name, s.email AS student_email, c.course_name " +
                     "FROM Enrollment e " +
                     "JOIN Person s ON e.student_id = s.person_id " +
                     "JOIN Course c ON e.course_id = c.course_id " +
                     "WHERE e.grade IS NULL";
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Enrollment enrollment = new Enrollment();
                enrollment.setEnrollmentId(rs.getInt("enrollment_id"));
                // Ensure that the student is created with both name and email
                enrollment.setStudent(new Student(rs.getInt("student_id"), rs.getString("student_name"), rs.getString("student_email")));
                enrollment.setCourse(new Course(rs.getString("course_name"), 0)); // Ensure Course has correct constructor
                enrollments.add(enrollment);
            }
        }
        return enrollments;
    }

    // Method to update the grade of an enrollment
    public void updateGrade(int enrollmentId, double grade) throws SQLException {
        String sql = "UPDATE Enrollment SET grade = ? WHERE enrollment_id = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, grade);
            stmt.setInt(2, enrollmentId);
            stmt.executeUpdate();
        }
    }
}


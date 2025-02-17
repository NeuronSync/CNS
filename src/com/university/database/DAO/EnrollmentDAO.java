package com.university.database.DAO;

import com.university.database.DatabaseConnector;
import com.university.model.Student;
import com.university.model.Course;
import java.sql.*;

public class EnrollmentDAO {
    public void enrollStudent(Student student, Course course) throws SQLException {
        String sql = "INSERT INTO Enrollment (student_id, course_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, student.getPersonId()); // Assume Student has getPersonId()
            stmt.setInt(2, course.getCourseId());
            stmt.executeUpdate();
        }
    }
}


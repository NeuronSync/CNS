package com.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentDAO {

    // Update Student Grade
    public boolean updateStudentGrade(int studentId, double grade) {
        String sql = "UPDATE enrollments SET grade = ? WHERE student_id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, grade);
            stmt.setInt(2, studentId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

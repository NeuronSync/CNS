package com.university.database.DAO;

import com.university.database.DatabaseConnector;
import com.university.model.Student;
import java.sql.*;

public class StudentDAO {
    public void addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO Person (name, email, role) VALUES (?, ?, 'student')";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getEmail());
            stmt.executeUpdate();
        }
    }
}

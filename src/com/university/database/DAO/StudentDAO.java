package com.university.database.DAO;

import java.util.List;
import java.util.ArrayList;
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
    public List<Student> getAllStudents() throws SQLException {
    List<Student> students = new ArrayList<>();
    String sql = "SELECT * FROM Person WHERE role='student'";
    try (Connection conn = DatabaseConnector.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        while (rs.next()) {
            students.add(new Student(
                rs.getString("name"),
                rs.getString("email")
            ));
        }
    }
    return students;
}
}

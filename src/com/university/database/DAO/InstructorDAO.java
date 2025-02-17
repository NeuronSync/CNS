package com.university.database.DAO;

import com.university.database.DatabaseConnector;
import java.util.List;
import java.util.ArrayList;
import com.university.model.Instructor;
import java.sql.*;

public class InstructorDAO {
    public void addInstructor(Instructor instructor) throws SQLException {
        String sql = "INSERT INTO Person (name, email, role) VALUES (?, ?, 'instructor')";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, instructor.getName());
            stmt.setString(2, instructor.getEmail());
            stmt.executeUpdate();
        }
    }

    public List<Instructor> getAllInstructors() throws SQLException {
        List<Instructor> instructors = new ArrayList<>();
        String sql = "SELECT * FROM Person WHERE role='instructor'";
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int personId = rs.getInt("person_id"); // Assuming person_id is in the database schema
                String name = rs.getString("name");
                String email = rs.getString("email");
                instructors.add(new Instructor(personId, name, email)); // Corrected to pass all 3 parameters
            }
        }
        return instructors;
    }
}


package com.university.dao;

import com.university.models.InstructorAssignment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstructorAssignmentDAO {

    // Assign an instructor to a course
    public boolean assignInstructor(int instructorId, int courseId) {
        String sql = "INSERT INTO instructor_assignments (instructor_id, course_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, instructorId);
            stmt.setInt(2, courseId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Reassign instructor to a new course
    public boolean reassignInstructor(int assignmentId, int newCourseId) {
        String sql = "UPDATE instructor_assignments SET course_id = ? WHERE assignment_id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newCourseId);
            stmt.setInt(2, assignmentId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Remove instructor assignment
    public boolean removeInstructorAssignment(int assignmentId) {
        String sql = "DELETE FROM instructor_assignments WHERE assignment_id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, assignmentId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get all instructor assignments
    public List<InstructorAssignment> getAllAssignments() {
        List<InstructorAssignment> assignments = new ArrayList<>();
        String sql = "SELECT ia.assignment_id, u.email, c.course_name, ia.assigned_date " +
                     "FROM instructor_assignments ia " +
                     "JOIN users u ON ia.instructor_id = u.user_id " +
                     "JOIN courses c ON ia.course_id = c.course_id";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                assignments.add(new InstructorAssignment(
                    rs.getInt("assignment_id"),
                    rs.getString("email"),
                    rs.getString("course_name"),
                    rs.getTimestamp("assigned_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assignments;
    }
}

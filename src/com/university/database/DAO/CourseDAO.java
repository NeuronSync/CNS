package com.university.database.DAO;

import com.university.database.DatabaseConnector;
import com.university.model.Course;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    public List<Course> getAllCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM Course";
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Course course = new Course();
                course.setCourseId(rs.getInt("course_id"));
                course.setCourseName(rs.getString("course_name"));
                courses.add(course);
            }
        }
        return courses;
    }
	    public void addCourse(Course course) throws SQLException {
	    String sql = "INSERT INTO Course (course_name, credits, instructor_id) VALUES (?, ?, ?)";
	    try (Connection conn = DatabaseConnector.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, course.getCourseName());
	        stmt.setInt(2, course.getCredits());
	        stmt.setInt(3, course.getInstructor().getPersonId()); // Assume Instructor has getPersonId()
	        stmt.executeUpdate();
	    }
	}
}

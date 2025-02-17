package com.university.analytics;

import com.university.database.DatabaseConnector;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ChartGenerator {

    public static void showEnrollmentChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String sql = "SELECT c.course_name, COUNT(e.student_id) AS enrollments " +
                     "FROM Course c LEFT JOIN Enrollment e ON c.course_id = e.course_id " +
                     "GROUP BY c.course_name";

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                dataset.addValue(rs.getInt("enrollments"), "Students", rs.getString("course_name"));
            }

            JFreeChart chart = ChartFactory.createBarChart(
                "Enrollments by Course", "Course", "Students", dataset
            );

            displayChart(chart, "Enrollment Analytics");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void showGradeDistribution() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        String sql = "SELECT " +
                     "CASE " +
                     "  WHEN grade >= 85 THEN 'A' " +
                     "  WHEN grade >= 70 THEN 'B' " +
                     "  WHEN grade >= 55 THEN 'C' " +
                     "  ELSE 'D' END AS grade_band, " +
                     "COUNT(*) AS count " +
                     "FROM Enrollment WHERE grade IS NOT NULL " +
                     "GROUP BY grade_band";

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                dataset.setValue(rs.getString("grade_band"), rs.getInt("count"));
            }

            JFreeChart chart = ChartFactory.createPieChart("Grade Distribution", dataset);
            displayChart(chart, "Grade Distribution");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayChart(JFreeChart chart, String title) {
        ChartFrame frame = new ChartFrame(title, chart);
        frame.setSize(600, 400);
        frame.setVisible(true);
    }
}


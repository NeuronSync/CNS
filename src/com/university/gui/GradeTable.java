package com.university.gui;

import com.university.database.DatabaseConnector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class GradeTable extends JFrame {
    private JTable table;
    private DefaultTableModel model;

    public GradeTable(String title, String query) {
        setTitle(title);
        model = new DefaultTableModel();
        table = new JTable(model);
        
        model.addColumn("Course");
        model.addColumn(title.contains("Average") ? "Average Grade" : "Student Grade");

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            ResultSetMetaData metaData = rs.getMetaData();
            while (rs.next()) {
                Object[] row = new Object[metaData.getColumnCount()];
                for (int i = 0; i < row.length; i++) {
                    row[i] = rs.getObject(i + 1);
                }
                model.addRow(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        add(new JScrollPane(table));
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    // For average grades
    public static void showAverageGrades() {
        String query = "SELECT c.course_name, AVG(e.grade) " +
                      "FROM Enrollment e " +
                      "JOIN Course c ON e.course_id = c.course_id " +
                      "GROUP BY c.course_name";
        new GradeTable("Average Grades per Course", query).setVisible(true);
    }

    // For student grades
    public static void showStudentGrades() {
        String query = "SELECT p.name, c.course_name, e.grade " +
                      "FROM Enrollment e " +
                      "JOIN Person p ON e.student_id = p.person_id " +
                      "JOIN Course c ON e.course_id = c.course_id";
        new GradeTable("Student Grades Overview", query).setVisible(true);
    }
}

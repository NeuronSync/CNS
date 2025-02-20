package com.university.gui.admin;

import com.university.dao.AnalyticsDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AnalyticsReporting extends JFrame {
    private AnalyticsDAO analyticsDAO;
    private JTable enrollmentTable, gradeTable, avgGradeTable;

    public AnalyticsReporting() {
        analyticsDAO = new AnalyticsDAO();

        setTitle("Analytics & Reporting");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Enrollment Analytics
        JPanel enrollmentPanel = new JPanel(new BorderLayout());
        enrollmentTable = new JTable(new DefaultTableModel(new String[]{"Course", "Total Enrollments"}, 0));
        loadEnrollmentAnalytics();
        enrollmentPanel.add(new JScrollPane(enrollmentTable), BorderLayout.CENTER);
        tabbedPane.addTab("Enrollments", enrollmentPanel);

        // Grade Distribution
        JPanel gradePanel = new JPanel(new BorderLayout());
        gradeTable = new JTable(new DefaultTableModel(new String[]{"Grade", "Student Count"}, 0));
        loadGradeDistribution();
        gradePanel.add(new JScrollPane(gradeTable), BorderLayout.CENTER);
        tabbedPane.addTab("Grade Distribution", gradePanel);

        // Average Grades
        JPanel avgGradePanel = new JPanel(new BorderLayout());
        avgGradeTable = new JTable(new DefaultTableModel(new String[]{"Course", "Average Grade"}, 0));
        loadAverageGrades();
        avgGradePanel.add(new JScrollPane(avgGradeTable), BorderLayout.CENTER);
        tabbedPane.addTab("Average Grades", avgGradePanel);

        add(tabbedPane);
    }

    // Load Enrollment Analytics into Table
    private void loadEnrollmentAnalytics() {
        DefaultTableModel model = (DefaultTableModel) enrollmentTable.getModel();
        model.setRowCount(0);
        List<String[]> data = analyticsDAO.getEnrollmentAnalytics();
        for (String[] row : data) {
            model.addRow(row);
        }
    }

    // Load Grade Distribution into Table
    private void loadGradeDistribution() {
        DefaultTableModel model = (DefaultTableModel) gradeTable.getModel();
        model.setRowCount(0);
        List<String[]> data = analyticsDAO.getGradeDistribution();
        for (String[] row : data) {
            model.addRow(row);
        }
    }

    // Load Average Grades into Table
    private void loadAverageGrades() {
        DefaultTableModel model = (DefaultTableModel) avgGradeTable.getModel();
        model.setRowCount(0);
        List<String[]> data = analyticsDAO.getAverageGrades();
        for (String[] row : data) {
            model.addRow(row);
        }
    }
}

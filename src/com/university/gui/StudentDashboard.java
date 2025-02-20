package com.university.gui.student;

import javax.swing.*;
import java.awt.*;

public class StudentDashboard extends JFrame {
    private int userId;

    // Constructor with userId parameter
    public StudentDashboard(int userId) {
        this.userId = userId;
        initializeUI();
    }

    // Default constructor (for backward compatibility)
    public StudentDashboard() {
        initializeUI();
    }

    // Initialize UI components
    private void initializeUI() {
        setTitle("Student Dashboard");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create UI Components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton btnViewCourses = new JButton("View Enrolled Courses");
        JButton btnTrackGrades = new JButton("Track Grades");
        JButton btnResources = new JButton("Access Study Materials");

        // Add Action Listeners
        btnViewCourses.addActionListener(e -> viewEnrolledCourses());
        btnTrackGrades.addActionListener(e -> trackGrades());
        btnResources.addActionListener(e -> accessStudyMaterials());

        // Add Buttons to Panel
        panel.add(btnViewCourses);
        panel.add(btnTrackGrades);
        panel.add(btnResources);

        add(panel);
    }

    // Feature Methods (To be implemented later)
    private void viewEnrolledCourses() {
        JOptionPane.showMessageDialog(this, "Feature: View Enrolled Courses for User ID: " + userId);
    }

    private void trackGrades() {
        JOptionPane.showMessageDialog(this, "Feature: Track Grades for User ID: " + userId);
    }

    private void accessStudyMaterials() {
        JOptionPane.showMessageDialog(this, "Feature: Access Study Materials for User ID: " + userId);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentDashboard().setVisible(true));
    }
}

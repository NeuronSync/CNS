package com.university.gui.student;

import com.university.dao.StudyMaterialDAO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

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
        panel.setLayout(new GridLayout(4, 1, 10, 10));

        JButton btnViewCourses = new JButton("View Enrolled Courses");
        JButton btnTrackGrades = new JButton("Track Grades");
        JButton btnResources = new JButton("Access Study Materials");
        JButton btnLogout = new JButton("Logout");

        // Add Action Listeners
        btnViewCourses.addActionListener(e -> viewEnrolledCourses());
        btnTrackGrades.addActionListener(e -> trackGrades());
        btnResources.addActionListener(e -> accessStudyMaterials());
        btnLogout.addActionListener(e -> logout());

        // Add Buttons to Panel
        panel.add(btnViewCourses);
        panel.add(btnTrackGrades);
        panel.add(btnResources);
        panel.add(btnLogout);

        add(panel);
    }

    // Feature Methods
    private void viewEnrolledCourses() {
        JOptionPane.showMessageDialog(this, "Feature: View Enrolled Courses for User ID: " + userId);
    }

    private void trackGrades() {
        JOptionPane.showMessageDialog(this, "Feature: Track Grades for User ID: " + userId);
    }

    private void accessStudyMaterials() {
        List<String> materials = StudyMaterialDAO.getMaterials();
        if (materials.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No study materials available.");
        } else {
            String message = String.join("\n", materials);
            JOptionPane.showMessageDialog(this, "Study Materials:\n" + message);
        }
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", 
                        "Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
	    new com.university.gui.LoginForm().setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentDashboard().setVisible(true));
    }
}


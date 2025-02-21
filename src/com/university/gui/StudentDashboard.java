package com.university.gui.student;

import com.university.dao.EnrollmentDAO;
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
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create UI Components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10)); // Increased grid size for new button

        JButton btnViewCourses = new JButton("View Enrolled Courses");
        JButton btnTrackGrades = new JButton("Track Grades");
        JButton btnResources = new JButton("Access Study Materials");
        JButton btnUpdateProfile = new JButton("Update Profile");
        JButton btnLogout = new JButton("Logout");

        // Add Action Listeners
        btnViewCourses.addActionListener(e -> viewEnrolledCourses());
        btnTrackGrades.addActionListener(e -> trackGrades());
        btnResources.addActionListener(e -> accessStudyMaterials());
        btnUpdateProfile.addActionListener(e -> openUpdateProfile());
        btnLogout.addActionListener(e -> logout());

        // Add Buttons to Panel
        panel.add(btnViewCourses);
        panel.add(btnTrackGrades);
        panel.add(btnResources);
        panel.add(btnUpdateProfile);
        panel.add(btnLogout);

        add(panel);
    }

    private void viewEnrolledCourses() {
    EnrollmentDAO enrollmentDAO = new EnrollmentDAO();  // Create an instance
    List<String> courses = enrollmentDAO.getEnrolledCoursesByStudentId(userId);

    if (courses.isEmpty()) {
        JOptionPane.showMessageDialog(this, "You are not enrolled in any courses.");
    } else {
        String message = String.join("\n", courses);
        JOptionPane.showMessageDialog(this, "Your Enrolled Courses:\n" + message);
    }
}

    private void trackGrades() {
    EnrollmentDAO enrollmentDAO = new EnrollmentDAO();  // Create an instance
    List<String> grades = enrollmentDAO.getStudentGrades(userId);

    if (grades.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No grades available yet.");
    } else {
        String message = String.join("\n", grades);
        JOptionPane.showMessageDialog(this, "Your Grades:\n" + message);
    }
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

    private void openUpdateProfile() {
        new UpdateStudentProfile(userId).setVisible(true);
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

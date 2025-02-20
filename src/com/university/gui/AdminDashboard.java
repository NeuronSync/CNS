package com.university.gui.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(600, 400);
        setLayout(new GridLayout(3, 2, 10, 10));

        JButton btnUserManagement = new JButton("User Management");
        JButton btnCourseManagement = new JButton("Course Management");
        JButton btnEnrollmentManagement = new JButton("Enrollment Management");
        JButton btnInstructorAssignment = new JButton("Instructor Assignment");
        JButton btnAnalyticsReporting = new JButton("Analytics & Reporting");
        JButton btnLogout = new JButton("Logout");

        btnUserManagement.addActionListener(e -> openUserManagement());
        btnCourseManagement.addActionListener(e -> openCourseManagement());
        btnEnrollmentManagement.addActionListener(e -> openEnrollmentManagement());
        btnInstructorAssignment.addActionListener(e -> openInstructorAssign());
        btnAnalyticsReporting.addActionListener(e -> openAnalyticsReporting());
        btnLogout.addActionListener(e -> logout());

        add(btnUserManagement);
        add(btnCourseManagement);
        add(btnEnrollmentManagement);
        add(btnInstructorAssignment);
        add(btnAnalyticsReporting);
        add(btnLogout);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void openUserManagement() {
        new UserManagement().setVisible(true);
    }

    private void openCourseManagement() {
        new CourseManagement().setVisible(true);
    }

    private void openEnrollmentManagement() {
        new EnrollmentManagement().setVisible(true);
    }

    private void openInstructorAssign() {
        new InstructorAssign().setVisible(true);
    }

    private void openAnalyticsReporting() {
        new AnalyticsReporting().setVisible(true);
    }

    private void logout() {
        JOptionPane.showMessageDialog(this, "Logged out successfully.");
        dispose();
        new com.university.gui.LoginForm().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminDashboard().setVisible(true));
    }
}

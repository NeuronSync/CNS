package com.university.gui.student;

import javax.swing.*;
import java.awt.*;

public class StudentDashboard extends JFrame {
    public StudentDashboard() {
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
        btnViewCourses.addActionListener(e -> JOptionPane.showMessageDialog(this, "Feature: View Enrolled Courses"));
        btnTrackGrades.addActionListener(e -> JOptionPane.showMessageDialog(this, "Feature: Track Grades"));
        btnResources.addActionListener(e -> JOptionPane.showMessageDialog(this, "Feature: Access Study Materials"));

        // Add Buttons to Panel
        panel.add(btnViewCourses);
        panel.add(btnTrackGrades);
        panel.add(btnResources);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentDashboard().setVisible(true));
    }
}

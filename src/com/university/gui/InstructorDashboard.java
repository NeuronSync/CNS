package com.university.gui.instructor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InstructorDashboard extends JFrame {
    public InstructorDashboard() {
        setTitle("Instructor Dashboard");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create UI Components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton btnViewCourses = new JButton("View Assigned Courses");
        JButton btnManageStudents = new JButton("Manage Student Grades");
        JButton btnPerformance = new JButton("View Performance Analytics");

        // Add Action Listeners
        btnViewCourses.addActionListener(e -> JOptionPane.showMessageDialog(this, "Feature: View Assigned Courses"));
        btnManageStudents.addActionListener(e -> JOptionPane.showMessageDialog(this, "Feature: Manage Student Grades"));
        btnPerformance.addActionListener(e -> JOptionPane.showMessageDialog(this, "Feature: View Performance Analytics"));

        // Add Buttons to Panel
        panel.add(btnViewCourses);
        panel.add(btnManageStudents);
        panel.add(btnPerformance);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InstructorDashboard().setVisible(true));
    }
}

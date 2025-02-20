package com.university.gui.instructor;

import javax.swing.*;
import java.awt.*;

public class InstructorDashboard extends JFrame {
    public InstructorDashboard(int instructorId) {
        setTitle("Instructor Dashboard");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));

        JButton btnViewCourses = new JButton("View Assigned Courses");
        JButton btnManageStudents = new JButton("Manage Student Grades");
        JButton btnPerformance = new JButton("View Performance Analytics");
        JButton btnUpdateProfile = new JButton("Update Profile");
        JButton btnLogout = new JButton("Logout");

        btnViewCourses.addActionListener(e -> new ViewAssignedCourses(instructorId).setVisible(true));
        btnManageStudents.addActionListener(e -> new ManageStudentGrades(instructorId).setVisible(true));
        btnPerformance.addActionListener(e -> new ViewPerformanceAnalytics(instructorId).setVisible(true));
        btnUpdateProfile.addActionListener(e -> new UpdateInstructorProfile(instructorId).setVisible(true));
        
        btnLogout.addActionListener(e -> {
		JOptionPane.showMessageDialog(this, "Logged out successfully.");
            dispose();
	    new com.university.gui.LoginForm().setVisible(true);
        });

        panel.add(btnViewCourses);
        panel.add(btnManageStudents);
        panel.add(btnPerformance);
        panel.add(btnUpdateProfile);
        panel.add(btnLogout);

        add(panel);
    }
}

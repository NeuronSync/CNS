package com.university.gui;

import javax.swing.*;

public class Dashboard extends JFrame {
    public Dashboard() {
        setTitle("University Course System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        JButton btnCourses = new JButton("View Courses");
        JButton btnEnroll = new JButton("Enroll Students");
        JButton btnAnalytics = new JButton("View Analytics");

	// In the Dashboard constructor:
	btnEnroll.addActionListener(e -> new EnrollmentForm(Dashboard.this).setVisible(true));
	panel.add(btnEnroll); // Replace previous enrollment button
	
        panel.add(btnCourses);
        panel.add(btnEnroll);
        panel.add(btnAnalytics);
        add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Dashboard());
    }
}

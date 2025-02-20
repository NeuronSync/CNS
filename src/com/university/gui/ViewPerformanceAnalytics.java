package com.university.gui.instructor;

import javax.swing.*;

public class ViewPerformanceAnalytics extends JFrame {
    public ViewPerformanceAnalytics(int instructorId) {
        setTitle("Performance Analytics");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Performance Analytics for Instructor ID: " + instructorId, SwingConstants.CENTER);
        add(label);
    }
}

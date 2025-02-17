package com.university.gui;

import com.university.analytics.EnrollmentAnalytics;
import com.university.analytics.GradeAnalytics;

import javax.swing.*;

public class Dashboard extends JFrame {
    public Dashboard() {
        setTitle("University Course System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        
        // Create buttons
        JButton btnCourses = new JButton("View Courses");
        JButton btnEnroll = new JButton("Enroll Students");
        JButton btnAnalytics = new JButton("View Analytics");
        JButton btnEnrollmentChart = new JButton("Enrollment Chart");
        JButton btnGradeChart = new JButton("Grade Distribution");
	JButton btnAddStudent = new JButton("Add Student");
	JButton btnAddInstructor = new JButton("Add Instructor");
	JButton btnAddCourse = new JButton("Add Course");
        btnEnroll.addActionListener(e -> new EnrollmentForm(Dashboard.this).setVisible(true));
	btnAddStudent.addActionListener(e -> new StudentForm(Dashboard.this).setVisible(true));
	btnAddInstructor.addActionListener(e -> new InstructorForm(Dashboard.this).setVisible(true));
        btnEnrollmentChart.addActionListener(e -> new EnrollmentAnalytics().generateChart());
        btnGradeChart.addActionListener(e -> new GradeAnalytics().generateChart());
	btnAddCourse.addActionListener(e -> new CourseForm(Dashboard.this).setVisible(true));
panel.add(btnAddCourse);

        // Add buttons in an organized way
	panel.add(btnAddInstructor);
	panel.add(btnAddStudent);
        panel.add(btnCourses);
        panel.add(btnEnroll);
        panel.add(btnAnalytics);
        panel.add(btnEnrollmentChart);
        panel.add(btnGradeChart);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Dashboard());
    }
}


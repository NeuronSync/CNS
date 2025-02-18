package com.university.gui;

import com.university.analytics.EnrollmentAnalytics;
import com.university.analytics.GradeAnalytics;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Dashboard extends JFrame {
    public Dashboard() {
        setTitle("University Course System");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Custom background panel with layout
        BackgroundPanel panel = new BackgroundPanel("/home/kectil/projects/CNS/lib/background.jpg");
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Spacing between components

        // Create and style buttons
        JButton btnEnroll = createStyledButton("Enroll Students");
        JButton btnEnrollmentChart = createStyledButton("Enrollment Chart");
        JButton btnGradeChart = createStyledButton("Grade Distribution");
        JButton btnAddStudent = createStyledButton("Add Student");
        JButton btnAddInstructor = createStyledButton("Add Instructor");
        JButton btnAddCourse = createStyledButton("Add Course");

        // Button actions
        btnEnroll.addActionListener(e -> new EnrollmentForm(Dashboard.this).setVisible(true));
        btnAddStudent.addActionListener(e -> new StudentForm(Dashboard.this).setVisible(true));
        btnAddInstructor.addActionListener(e -> new InstructorForm(Dashboard.this).setVisible(true));
        btnEnrollmentChart.addActionListener(e -> new EnrollmentAnalytics().generateChart());
        btnGradeChart.addActionListener(e -> new GradeAnalytics().generateChart());
        btnAddCourse.addActionListener(e -> new CourseForm(Dashboard.this).setVisible(true));

        // Add buttons with GridBagLayout
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(btnAddCourse, gbc);

        gbc.gridx = 1;
        panel.add(btnAddInstructor, gbc);

        gbc.gridx = 2;
        panel.add(btnAddStudent, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(btnEnroll, gbc);

        gbc.gridx = 1;
        panel.add(btnEnrollmentChart, gbc);

        gbc.gridx = 2;
        panel.add(btnGradeChart, gbc);

        add(panel);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(50, 150, 250));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Dashboard::new);
    }
}

// Custom JPanel for background image
class BackgroundPanel extends JPanel {
    private BufferedImage backgroundImage;

    public BackgroundPanel(String imagePath) {
        try {
            backgroundImage = ImageIO.read(new File(imagePath));
        } catch (Exception e) {
            System.err.println("Error loading background image: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}


package com.university.gui.admin;

import com.university.gui.LoginForm;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboard extends JFrame {
    
    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);

        // Main panel with background
        BackgroundPanel mainPanel = new BackgroundPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Dashboard Grid
        JPanel gridPanel = createDashboardGrid();
        mainPanel.add(gridPanel, BorderLayout.CENTER);

        // Logout Panel
        JPanel logoutPanel = createLogoutPanel();
        mainPanel.add(logoutPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        JLabel title = new JLabel("Administrator Dashboard");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(255, 255, 255));
        panel.add(title);
        return panel;
    }

    private JPanel createDashboardGrid() {
        JPanel gridPanel = new JPanel(new GridLayout(2, 3, 30, 30));
        gridPanel.setOpaque(false);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] buttons = {
            "User Management", "Course Management", "Enrollment Management",
            "Instructor Assignment", "Analytics & Reporting", "System Settings"
        };

        for (String text : buttons) {
            JButton btn = createDashboardButton(text);
            gridPanel.add(btn);
        }

        // Add action listeners
        Component[] components = gridPanel.getComponents();
        components[0].addMouseListener(createListener(this::openUserManagement));
        components[1].addMouseListener(createListener(this::openCourseManagement));
        components[2].addMouseListener(createListener(this::openEnrollmentManagement));
        components[3].addMouseListener(createListener(this::openInstructorAssign));
        components[4].addMouseListener(createListener(this::openAnalyticsReporting));
        components[5].addMouseListener(createListener(() -> 
            JOptionPane.showMessageDialog(this, "System Settings")));

        return gridPanel;
    }

    private JButton createDashboardButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(70, 130, 180));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 150, 200));
                button.setFont(new Font("Segoe UI", Font.BOLD, 19));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180));
                button.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            }
        });

        return button;
    }

    private JPanel createLogoutPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setOpaque(false);
        
        JButton btnLogout = new JButton("Logout");
        btnLogout.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setBackground(new Color(220, 50, 50));
        btnLogout.setBorderPainted(false);
        btnLogout.setFocusPainted(false);
        btnLogout.addActionListener(e -> logout());
        
        panel.add(btnLogout);
        return panel;
    }

    private java.awt.event.MouseAdapter createListener(Runnable action) {
        return new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                action.run();
            }
        };
    }

    // Original methods remain unchanged
    private void openUserManagement() { new UserManagement().setVisible(true); }
    private void openCourseManagement() { new CourseManagement().setVisible(true); }
    private void openEnrollmentManagement() { new EnrollmentManagement().setVisible(true); }
    private void openInstructorAssign() { new InstructorAssign().setVisible(true); }
    private void openAnalyticsReporting() { new AnalyticsReporting().setVisible(true); }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to logout?", "Confirm Logout", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            new LoginForm().setVisible(true);
        }
    }

    // Background panel (same as LoginForm)
    private class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            try {
                backgroundImage = new ImageIcon("lib/background.jpg").getImage();
            } catch (Exception e) {
                setBackground(new Color(40, 40, 40));
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new AdminDashboard().setVisible(true);
        });
    }
}

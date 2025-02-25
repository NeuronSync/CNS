package com.university.gui.student;

import javax.swing.table.DefaultTableModel;
import com.university.dao.EnrollmentDAO;
import com.university.dao.StudyMaterialDAO;
import com.university.gui.LoginForm;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StudentDashboard extends JFrame {
    private int userId;
    private JPanel mainContentPanel;

    public StudentDashboard(int userId) {
        this.userId = userId;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Student Dashboard");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create main container
        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create sidebar
        JPanel sidebar = createSidebar();
        container.add(sidebar, BorderLayout.WEST);

        // Create main content area
        mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        showDefaultContent();
        container.add(mainContentPanel, BorderLayout.CENTER);

        add(container);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(250, getHeight()));
        sidebar.setBackground(new Color(34, 40, 49));

        // Profile section
        JPanel profilePanel = new JPanel();
        profilePanel.setBackground(new Color(34, 40, 49));
        profilePanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 30, 10));
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        
        JLabel profileIcon = new JLabel("👨🎓");
        profileIcon.setFont(new Font("Segoe UI", Font.PLAIN, 48));
        profileIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel welcomeLabel = new JLabel("Welcome, Student");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        profilePanel.add(profileIcon);
        profilePanel.add(Box.createVerticalStrut(10));
        profilePanel.add(welcomeLabel);
        
        // Navigation buttons
        JPanel navPanel = new JPanel();
        navPanel.setBackground(new Color(34, 40, 49));
        navPanel.setLayout(new GridLayout(0, 1, 10, 10));
        navPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        String[] buttonLabels = {
            "📚 Enrolled Courses",
            "📝 Track Grades",
            "📂 Study Materials",
            "👤 Update Profile"
        };

        for (String label : buttonLabels) {
            JButton btn = createNavButton(label);
            navPanel.add(btn);
        }

        // Logout button
        JButton btnLogout = new JButton("🚪 Logout");
        styleLogoutButton(btnLogout);
        btnLogout.addActionListener(e -> logout());

        sidebar.add(profilePanel, BorderLayout.NORTH);
        sidebar.add(navPanel, BorderLayout.CENTER);
        sidebar.add(btnLogout, BorderLayout.SOUTH);

        return sidebar;
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(57, 62, 70));
        button.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.LEFT);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 173, 181));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(57, 62, 70));
            }
        });

        // Add action listeners
        switch (text) {
            case "📚 Enrolled Courses":
                button.addActionListener(e -> showEnrolledCourses());
                break;
            case "📝 Track Grades":
                button.addActionListener(e -> showGrades());
                break;
            case "📂 Study Materials":
                button.addActionListener(e -> showStudyMaterials());
                break;
            case "👤 Update Profile":
                button.addActionListener(e -> openUpdateProfile());
                break;
        }

        return button;
    }

    private void styleLogoutButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(231, 76, 60));
        button.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(192, 57, 43));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(231, 76, 60));
            }
        });
    }

    private void showDefaultContent() {
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(Color.WHITE);
        
        JLabel welcome = new JLabel("Student Dashboard", SwingConstants.CENTER);
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcome.setForeground(new Color(34, 40, 49));
        
        content.add(welcome, BorderLayout.CENTER);
        updateMainContent(content);
    }

    private void showEnrolledCourses() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                List<String> courses = new EnrollmentDAO().getEnrolledCoursesByStudentId(userId);
                SwingUtilities.invokeLater(() -> {
                    JPanel panel = createTablePanel(courses, "Enrolled Courses", "Course Name");
                    updateMainContent(panel);
                });
                return null;
            }
        };
        worker.execute();
    }

    private void showGrades() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                List<String> grades = new EnrollmentDAO().getStudentGrades(userId);
                SwingUtilities.invokeLater(() -> {
                    JPanel panel = createTablePanel(grades, "Your Grades", "Grade Details");
                    updateMainContent(panel);
                });
                return null;
            }
        };
        worker.execute();
    }

    private void showStudyMaterials() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                List<String> materials = StudyMaterialDAO.getMaterials();
                SwingUtilities.invokeLater(() -> {
                    JPanel panel = createTablePanel(materials, "Study Materials", "Material Name");
                    updateMainContent(panel);
                });
                return null;
            }
        };
        worker.execute();
    }

    private JPanel createTablePanel(List<String> items, String title, String columnName) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        String[] columnNames = {columnName};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        
        for (String item : items) {
            model.addRow(new Object[]{item});
        }
        
        JScrollPane scrollPane = new JScrollPane(table);
        styleTable(table);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(34, 40, 49));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setGridColor(new Color(238, 238, 238));
    }

    private void updateMainContent(JPanel content) {
        mainContentPanel.removeAll();
        mainContentPanel.add(content, BorderLayout.CENTER);
        mainContentPanel.revalidate();
        mainContentPanel.repaint();
    }

    private void openUpdateProfile() {
        new UpdateStudentProfile(userId).setVisible(true);
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "<html><div style='width:300px;text-align:center'>Are you sure you want to log out?</div></html>", 
            "Logout", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            new LoginForm().setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                new StudentDashboard(1).setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

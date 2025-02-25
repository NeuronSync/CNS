package com.university.gui.instructor;

import java.awt.event.ActionListener;
import com.university.gui.LoginForm;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class InstructorDashboard extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(48, 63, 159);
    private static final Color HOVER_COLOR = new Color(63, 81, 181);
    private static final Color LOGOUT_COLOR = new Color(198, 40, 40);

    public InstructorDashboard(int instructorId) {
        setTitle("Instructor Dashboard");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, 1200, 800, 20, 20));

        // Main container with shadow effect
        JPanel mainContainer = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.fillRoundRect(5, 5, getWidth()-10, getHeight()-10, 25, 25);
            }
        };
        mainContainer.setOpaque(false);
        mainContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Background panel setup
        BackgroundPanel mainPanel = new BackgroundPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Header panel
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = createButtonPanel(instructorId);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Logout panel
        JPanel logoutPanel = createLogoutPanel();
        mainPanel.add(logoutPanel, BorderLayout.SOUTH);

        mainContainer.add(mainPanel);
        add(mainContainer);

        // Add window controls
        addWindowControls();
    }

    private void addWindowControls() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlPanel.setOpaque(false);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnMinimize = createControlButton("－", new Color(255, 193, 7));
        JButton btnClose = createControlButton("×", LOGOUT_COLOR);

        btnMinimize.addActionListener(e -> setState(Frame.ICONIFIED));
        btnClose.addActionListener(e -> dispose());

        controlPanel.add(btnMinimize);
        controlPanel.add(btnClose);

        add(controlPanel, BorderLayout.NORTH);
    }

    private JButton createControlButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setOpaque(true);
                button.repaint();
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setOpaque(false);
                button.repaint();
            }
        });

        return button;
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        JLabel title = new JLabel("Instructor Portal");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        
        // Add decorative line
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(255, 255, 255, 100));
        separator.setPreferredSize(new Dimension(300, 2));
        
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(title, gbc);
        panel.add(separator, gbc);
        
        return panel;
    }

    private JPanel createButtonPanel(int instructorId) {
        JPanel panel = new JPanel(new GridLayout(2, 2, 30, 30));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Create buttons with icons
        JButton[] buttons = {
            createDashboardButton("View Assigned Courses", "📚", e -> 
                new ViewAssignedCourses(instructorId).setVisible(true)),
            createDashboardButton("Manage Student Grades", "📝", e -> 
                new ManageStudentGrades(instructorId).setVisible(true)),
            createDashboardButton("Performance Analytics", "📊", e -> 
                new ViewPerformanceAnalytics(instructorId).setVisible(true)),
            createDashboardButton("Update Profile", "👤", e -> 
                new UpdateInstructorProfile(instructorId).setVisible(true))
        };

        for (JButton btn : buttons) {
            JPanel cardPanel = createCardPanel(btn);
            panel.add(cardPanel);
        }

        return panel;
    }

    private JPanel createCardPanel(JButton button) {
        JPanel card = new JPanel(new BorderLayout());
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Add depth effect
        JPanel innerPanel = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 30));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
            }
        };
        innerPanel.setOpaque(false);
        innerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        innerPanel.add(button);
        
        card.add(innerPanel);
        return card;
    }

    private JButton createDashboardButton(String text, String icon, ActionListener listener) {
        JButton button = new JButton("<html><div style='text-align:center'>" + 
            "<div style='font-size:32px; margin-bottom:10px'>" + icon + "</div>" + 
            text + "</div></html>");
        
        button.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(255, 255, 255, 30));
        button.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(listener);
        button.setOpaque(false);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 255, 255, 50));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 255, 255, 30));
            }
        });

        return button;
    }

    private JPanel createLogoutPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setOpaque(false);
        
        JButton btnLogout = new JButton("Logout");
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setBackground(new Color(255, 255, 255, 30));
        btnLogout.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogout.addActionListener(e -> logout());

        // Add hover effect
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogout.setBackground(new Color(220, 50, 50, 200));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogout.setBackground(new Color(255, 255, 255, 30));
            }
        });
        
        panel.add(btnLogout);
        return panel;
    }

    private void logout() {
        JDialog dialog = new JDialog(this, "Confirm Logout", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setUndecorated(true);
        dialog.setShape(new RoundRectangle2D.Double(0, 0, 400, 200, 20, 20));

        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        content.setBackground(new Color(255, 255, 255, 230));

        JLabel message = new JLabel("<html><div style='text-align:center'>Are you sure you want to log out?</div></html>");
        message.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        message.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);

        JButton btnYes = createDialogButton("Yes", LOGOUT_COLOR);
        JButton btnNo = createDialogButton("No", PRIMARY_COLOR);

        btnYes.addActionListener(e -> {
            dispose();
            new LoginForm().setVisible(true);
            dialog.dispose();
        });

        btnNo.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnYes);
        buttonPanel.add(btnNo);

        content.add(message, BorderLayout.CENTER);
        content.add(buttonPanel, BorderLayout.SOUTH);
        dialog.add(content);
        dialog.setVisible(true);
    }

    private JButton createDialogButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker()),
            BorderFactory.createEmptyBorder(8, 25, 8, 25)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private class BackgroundPanel extends JPanel {
        private BufferedImage backgroundImage;

        public BackgroundPanel() {
            try {
                backgroundImage = ImageIO.read(new File("lib/background.jpg"));
            } catch (IOException e) {
                setBackground(new Color(30, 30, 30));
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (backgroundImage != null) {
                g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
            
            // Add overlay gradient
            GradientPaint gradient = new GradientPaint(0, 0, new Color(12, 32, 56, 200), 
                getWidth(), getHeight(), new Color(24, 48, 96, 200));
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                new InstructorDashboard(1).setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

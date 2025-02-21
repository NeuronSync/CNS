package com.university.gui;

import com.university.dao.UserDAO;
import com.university.models.User;
import com.university.auth.SessionManager;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.imageio.ImageIO;
import com.university.gui.admin.AdminDashboard;
import com.university.gui.instructor.InstructorDashboard;
import com.university.gui.student.StudentDashboard;

public class LoginForm extends JFrame {
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginForm() {
        setTitle("University System Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Background panel with image
        BackgroundPanel backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(new GridBagLayout());

        // Login form panel
        JPanel loginPanel = createLoginPanel();
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(loginPanel, gbc);

        add(backgroundPanel);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        panel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Title
        JLabel titleLabel = new JLabel("University Login");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(255, 255, 255));
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(titleLabel, gbc);

        // Email Field
        gbc.gridy++;
        gbc.gridwidth = 1;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailLabel.setForeground(Color.WHITE);
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        styleTextField(txtEmail);
        panel.add(txtEmail, gbc);

        // Password Field
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordLabel.setForeground(Color.WHITE);
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        txtPassword = new JPasswordField(20);
        styleTextField(txtPassword);
        panel.add(txtPassword, gbc);

        // Login Button
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        btnLogin = new JButton("Sign In");
        styleButton(btnLogin);
        panel.add(btnLogin, gbc);

        btnLogin.addActionListener(e -> authenticateUser());

        return panel;
    }

    private void styleTextField(JTextField textField) {
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        textField.setBackground(new Color(255, 255, 255, 200));
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 120, 215));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 90, 180));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 120, 215));
            }
        });
    }

    // Background panel with image
    // Background panel with image
private class BackgroundPanel extends JPanel {
    private BufferedImage backgroundImage;

    public BackgroundPanel() {
        try {
            // Try loading from current directory first
            File imageFile = new File("lib/background.jpg");
            if (!imageFile.exists()) {
                // Fallback to classpath resources
                imageFile = new File(getClass().getClassLoader().getResource("background.jpg").getFile());
            }
            backgroundImage = ImageIO.read(imageFile);
        } catch (IOException | NullPointerException e) {
            System.err.println("Error loading background image: " + e.getMessage());
            // Set default background color if image fails to load
            setBackground(new Color(30, 30, 30));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            g2d.dispose();
        }
    }
}

    private void authenticateUser() {
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email and password are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        UserDAO userDAO = new UserDAO();
        User user = userDAO.authenticateByEmail(email, password);

        if (user != null) {
            if (isAccountLocked(user.getLockedUntil())) {
                JOptionPane.showMessageDialog(this, "Account is locked. Try again later.", "Account Locked", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Set the logged-in user
            SessionManager.setCurrentUser(user);
            dispose();
            openDashboard(user);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid email or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isAccountLocked(Timestamp lockedUntil) {
        return lockedUntil != null && lockedUntil.toLocalDateTime().isAfter(LocalDateTime.now());
    }

    private void openDashboard(User user) {
        String role = user.getRole().toLowerCase();
        
        switch (role) {
            case "admin":
                new AdminDashboard().setVisible(true);
                break;
            case "instructor":
                int instructorId = new UserDAO().getInstructorId(user.getEmail());
                new InstructorDashboard(instructorId).setVisible(true);
                break;
            case "student":
                new StudentDashboard(user.getUserId()).setVisible(true);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Unknown role: " + role, "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new LoginForm().setVisible(true);
        });
    }
}

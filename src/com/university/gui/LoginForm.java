package com.university.gui;

import com.university.dao.UserDAO;
import com.university.models.User;
import com.university.auth.SessionManager;
import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import com.university.gui.admin.AdminDashboard;
import com.university.gui.instructor.InstructorDashboard;
import com.university.gui.student.StudentDashboard;

public class LoginForm extends JFrame {
    private JTextField txtEmail = new JTextField(20);
    private JPasswordField txtPassword = new JPasswordField(20);
    private JButton btnLogin = new JButton("Login");

    public LoginForm() {
        setTitle("University System Login");
        setSize(350, 200);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Email:"));
        add(txtEmail);
        add(new JLabel("Password:"));
        add(txtPassword);
        add(btnLogin);

        btnLogin.addActionListener(e -> authenticateUser());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void authenticateUser() {
        String email = txtEmail.getText();
        String password = new String(txtPassword.getPassword());

        User user = new UserDAO().authenticateByEmail(email, password);

        if (user != null) {
            // Check if account is locked
            if (isAccountLocked(user.getLockedUntil())) {
                JOptionPane.showMessageDialog(this, "Account locked. Try again later.");
                return;
            }

            // Successful login
            SessionManager.setCurrentUser(user);
            dispose();
            openDashboard(user.getRole());
        } else {
            JOptionPane.showMessageDialog(this, "Invalid email or password!");
        }
    }

    private boolean isAccountLocked(Timestamp lockedUntil) {
        return lockedUntil != null && lockedUntil.toLocalDateTime().isAfter(LocalDateTime.now());
    }

    private void openDashboard(String role) {
        switch (role.toLowerCase()) {
            case "admin":
                new AdminDashboard().setVisible(true);
                break;
            case "instructor":
                new InstructorDashboard().setVisible(true);
                break;
            case "student":
                new StudentDashboard().setVisible(true);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Unknown role: " + role);
                break;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}

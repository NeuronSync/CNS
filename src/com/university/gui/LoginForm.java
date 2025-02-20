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
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}


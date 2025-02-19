package com.university.gui;

import com.university.dao.UserDAO;
import com.university.models.User;
import com.university.auth.SessionManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame {
    private JTextField txtUsername = new JTextField(20);
    private JPasswordField txtPassword = new JPasswordField(20);
    private JButton btnLogin = new JButton("Login");

    public LoginForm() {
        setTitle("University System Login");
        setSize(300, 200);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Username:"));
        add(txtUsername);
        add(new JLabel("Password:"));
        add(txtPassword);
        add(btnLogin);

        btnLogin.addActionListener(e -> authenticateUser());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void authenticateUser() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        User user = new UserDAO().authenticate(username, password);

        if (user != null) {
            SessionManager.setCurrentUser(user);
            dispose();
            switch (user.getRole()) {
                case "admin":
                    new AdminDashboard().setVisible(true);
                    break;
                case "instructor":
                    new InstructorDashboard().setVisible(true);
                    break;
                case "student":
                    new StudentDashboard().setVisible(true);
                    break;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}

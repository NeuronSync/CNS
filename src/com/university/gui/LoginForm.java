package com.university.gui;

import com.university.database.DatabaseConnector;
import org.mindrot.jbcrypt.BCrypt;
import javax.swing.*;
import java.sql.*;

public class LoginForm extends JDialog {
    private JTextField txtUsername = new JTextField(20);
    private JPasswordField txtPassword = new JPasswordField(20);
    private JButton btnLogin = new JButton("Login");

    public LoginForm(JFrame parent) {
        super(parent, "University System Login", true);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setSize(300, 200);

        add(new JLabel("Username:"));
        add(txtUsername);
        add(new JLabel("Password:"));
        add(txtPassword);
        add(btnLogin);

        btnLogin.addActionListener(e -> authenticateUser());

        setLocationRelativeTo(parent);
    }

    private void authenticateUser() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        String sql = "SELECT password_hash FROM users WHERE username = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next() && BCrypt.checkpw(password, rs.getString("password_hash"))) {
                dispose();
                new Dashboard().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm(null).setVisible(true));
    }
}

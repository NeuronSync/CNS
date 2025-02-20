package com.university.gui.instructor;

import com.university.dao.InstructorDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class UpdateInstructorProfile extends JFrame {
    private InstructorDAO instructorDAO = new InstructorDAO();
    private JTextField txtName, txtEmail;
    private JPasswordField txtPassword;
    private int instructorId;

    public UpdateInstructorProfile(int instructorId) {
        this.instructorId = instructorId;
        setTitle("Update Profile");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.add(new JLabel("Name:"));
        txtName = new JTextField();
        panel.add(txtName);

        panel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panel.add(txtEmail);

        panel.add(new JLabel("New Password:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        JButton btnUpdate = new JButton("Update Profile");
        btnUpdate.addActionListener(this::updateProfile);
        panel.add(btnUpdate);

        add(panel);
    }

    private void updateProfile(ActionEvent e) {
        String name = txtName.getText();
        String email = txtEmail.getText();
        String password = new String(txtPassword.getPassword());

        boolean success = instructorDAO.updateProfile(instructorId, name, email);
        if (!password.isEmpty()) {
            success &= instructorDAO.changePassword(instructorId, password);
        }

        if (success) {
            JOptionPane.showMessageDialog(this, "Profile updated successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update profile.");
        }
    }
}

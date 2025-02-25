package com.university.gui.student;

import com.university.dao.StudentDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class UpdateStudentProfile extends JFrame {
    private StudentDAO studentDAO = new StudentDAO();
    private JTextField txtName, txtEmail, txtPhone;
    private JPasswordField txtPassword;
    private int studentId;

    public UpdateStudentProfile(int studentId) {
        this.studentId = studentId;
        setTitle("Update Profile");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        panel.add(new JLabel("Name:"));
        txtName = new JTextField();
        panel.add(txtName);

        panel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panel.add(txtEmail);

        panel.add(new JLabel("Phone Number:"));
        txtPhone = new JTextField();
        panel.add(txtPhone);

        panel.add(new JLabel("New Password:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        JButton btnUpdate = new JButton("Update Profile");
        btnUpdate.addActionListener(this::updateProfile);
        panel.add(btnUpdate);

        add(panel);
    }

    private void updateProfile(ActionEvent e) {
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        boolean success = studentDAO.updateProfile(studentId, name, email, phone);
        if (!password.isEmpty()) {
            success &= studentDAO.changePassword(studentId, password);
        }

        if (success) {
            JOptionPane.showMessageDialog(this, "Profile updated successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update profile.");
        }
    }
}

package com.university.gui;

import com.university.dao.UserDAO;
import com.university.models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminDashboard extends JFrame {
    private UserDAO userDAO;

    public AdminDashboard() {
        userDAO = new UserDAO();

        setTitle("Admin Dashboard");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Buttons
        JButton addInstructorBtn = new JButton("Add Instructor");
        JButton addStudentBtn = new JButton("Add Student");
        JButton viewUsersBtn = new JButton("View Users");
        JButton resetPasswordBtn = new JButton("Reset Password");
        JButton deleteUserBtn = new JButton("Delete User");

        // Action Listeners
        addInstructorBtn.addActionListener(e -> addUser("Instructor", "Instructor"));
        addStudentBtn.addActionListener(e -> addUser("Student", "Student"));
        viewUsersBtn.addActionListener(e -> viewUsers());
        resetPasswordBtn.addActionListener(e -> resetPassword());
        deleteUserBtn.addActionListener(e -> deleteUser());

        // Layout
        setLayout(new GridLayout(5, 1, 10, 10));
        add(addInstructorBtn);
        add(addStudentBtn);
        add(viewUsersBtn);
        add(resetPasswordBtn);
        add(deleteUserBtn);
    }

    // Add Instructor or Student
    private void addUser(String role, String defaultPassword) {
        String username = JOptionPane.showInputDialog(this, "Enter " + role + " Username:");
        if (username != null && !username.trim().isEmpty()) {
            boolean success = userDAO.addUser(username, defaultPassword, role);
            JOptionPane.showMessageDialog(this, success ? role + " Added Successfully!" : "Error Adding " + role);
        }
    }

    // View All Users
    private void viewUsers() {
        List<User> users = userDAO.getAllUsers();
        StringBuilder userList = new StringBuilder("User List:\n");
        for (User user : users) {
            userList.append(user.getUserId()).append(" - ")
                    .append(user.getUsername()).append(" (").append(user.getRole()).append(")\n");
        }
        JOptionPane.showMessageDialog(this, userList.toString());
    }

    // Reset Password
    private void resetPassword() {
        String username = JOptionPane.showInputDialog(this, "Enter Username to Reset Password:");
        if (username != null && !username.trim().isEmpty()) {
            String newPassword = JOptionPane.showInputDialog(this, "Enter New Password:");
            if (newPassword != null && !newPassword.trim().isEmpty()) {
                boolean success = userDAO.updatePassword(username, newPassword);
                JOptionPane.showMessageDialog(this, success ? "Password Reset Successfully!" : "Error Resetting Password");
            }
        }
    }

    // Delete User
    private void deleteUser() {
        String username = JOptionPane.showInputDialog(this, "Enter Username to Delete:");
        if (username != null && !username.trim().isEmpty()) {
            boolean success = userDAO.deleteUser(username);
            JOptionPane.showMessageDialog(this, success ? "User Deleted Successfully!" : "Error Deleting User");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminDashboard().setVisible(true));
    }
}

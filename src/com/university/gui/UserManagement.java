package com.university.gui.admin;

import com.university.dao.UserDAO;
import com.university.models.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class UserManagement extends JFrame {
    private JTable userTable;
    private DefaultTableModel tableModel;
    private UserDAO userDAO = new UserDAO();

    public UserManagement() {
        setTitle("User Management");
        setSize(700, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Table setup
        String[] columnNames = {"User ID", "Email", "Username", "Role"};
        tableModel = new DefaultTableModel(columnNames, 0);
        userTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(userTable);
        add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton btnAdd = new JButton("Add User");
        JButton btnEdit = new JButton("Edit User");
        JButton btnDelete = new JButton("Delete User");

        btnAdd.addActionListener(this::addUser);
        btnEdit.addActionListener(this::editUser);
        btnDelete.addActionListener(this::deleteUser);

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        add(buttonPanel, BorderLayout.SOUTH);

        loadUsers();
    }

    private void loadUsers() {
        tableModel.setRowCount(0); // Clear table
        List<User> users = userDAO.getAllUsers();
        for (User user : users) {
            tableModel.addRow(new Object[]{user.getUserId(), user.getEmail(), user.getUsername(), user.getRole()});
        }
    }

    private void addUser(ActionEvent e) {
        JTextField txtEmail = new JTextField();
        JTextField txtUsername = new JTextField();
        JTextField txtRole = new JTextField();
        JPasswordField txtPassword = new JPasswordField();

        Object[] message = {
            "Email:", txtEmail,
            "Username:", txtUsername,
            "Role:", txtRole,
            "Password:", txtPassword
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add User", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            boolean success = userDAO.addUser(
                txtEmail.getText(),
                txtUsername.getText(),
                txtRole.getText(),
                new String(txtPassword.getPassword())
            );

            if (success) {
                JOptionPane.showMessageDialog(this, "User added successfully!");
                loadUsers();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add user.");
            }
        }
    }

    private void editUser(ActionEvent e) {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to edit.");
            return;
        }

        int userId = (int) tableModel.getValueAt(selectedRow, 0);
        String currentEmail = (String) tableModel.getValueAt(selectedRow, 1);
        String currentUsername = (String) tableModel.getValueAt(selectedRow, 2);
        String currentRole = (String) tableModel.getValueAt(selectedRow, 3);

        JTextField txtEmail = new JTextField(currentEmail);
        JTextField txtUsername = new JTextField(currentUsername);
        JTextField txtRole = new JTextField(currentRole);

        Object[] message = {
            "Email:", txtEmail,
            "Username:", txtUsername,
            "Role:", txtRole
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Edit User", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            boolean success = userDAO.updateUser(
                userId,
                txtEmail.getText(),
                txtUsername.getText(),
                txtRole.getText()
            );

            if (success) {
                JOptionPane.showMessageDialog(this, "User updated successfully!");
                loadUsers();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update user.");
            }
        }
    }

    private void deleteUser(ActionEvent e) {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.");
            return;
        }

        int userId = (int) tableModel.getValueAt(selectedRow, 0);
        int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user?", "Delete User", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            boolean success = userDAO.deleteUserById(userId);
            if (success) {
                JOptionPane.showMessageDialog(this, "User deleted successfully!");
                loadUsers();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete user.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserManagement().setVisible(true));
    }
}

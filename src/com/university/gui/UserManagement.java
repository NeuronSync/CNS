package com.university.gui.admin;

import com.university.dao.UserDAO;
import com.university.models.User;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class UserManagement extends JFrame {
    private JTable userTable;
    private DefaultTableModel tableModel;
    private UserDAO userDAO = new UserDAO();

    public UserManagement() {
        setTitle("User Management");
        setSize(1000, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Main container with padding
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 245, 245));
        
        // Table setup with modern styling
        String[] columnNames = {"User ID", "Email", "Username", "Role"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return false; // Disable table editing
            }
        };
        
        userTable = createStyledTable();
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        // Button panel with modern buttons
        JPanel buttonPanel = createButtonPanel();
        
        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(new Color(245, 245, 245));
        JLabel titleLabel = new JLabel("User Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(60, 60, 60));
        headerPanel.add(titleLabel);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        loadUsers();
    }

    private JTable createStyledTable() {
        JTable table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(35);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 5));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Header styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);
        
        // Renderer for alternating row colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, 
                        isSelected, hasFocus, row, column);
                
                if (isSelected) {
                    c.setBackground(new Color(220, 240, 255));
                } else {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(250, 250, 250));
                }
                return c;
            }
        });
        
        return table;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panel.setBackground(new Color(245, 245, 245));
        
        JButton btnAdd = createModernButton("Add User", new Color(70, 130, 180));
        JButton btnEdit = createModernButton("Edit User", new Color(100, 150, 100));
        JButton btnDelete = createModernButton("Delete User", new Color(200, 80, 80));
        
        btnAdd.addActionListener(this::addUser);
        btnEdit.addActionListener(this::editUser);
        btnDelete.addActionListener(this::deleteUser);
        
        panel.add(btnAdd);
        panel.add(btnEdit);
        panel.add(btnDelete);
        
        return panel;
    }

    private JButton createModernButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker()),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        
        return button;
    }

    private void loadUsers() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                tableModel.setRowCount(0);
                List<User> users = userDAO.getAllUsers();
                for (User user : users) {
                    tableModel.addRow(new Object[]{
                        user.getUserId(), 
                        user.getEmail(), 
                        user.getUsername(), 
                        user.getRole()
                    });
                }
                return null;
            }
        };
        worker.execute();
    }

    private void addUser(ActionEvent e) {
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextField txtEmail = createFormField("Email");
        JTextField txtUsername = createFormField("Username");
        JTextField txtRole = createFormField("Role");
        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setBorder(BorderFactory.createTitledBorder("Password"));
        
        formPanel.add(new JLabel("Email:"));
        formPanel.add(txtEmail);
        formPanel.add(new JLabel("Username:"));
        formPanel.add(txtUsername);
        formPanel.add(new JLabel("Role:"));
        formPanel.add(txtRole);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(txtPassword);
        
        int option = JOptionPane.showConfirmDialog(this, formPanel, 
                "Add New User", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (option == JOptionPane.OK_OPTION) {
            // Validation and database operations remain the same
        }
    }

    private JTextField createFormField(String title) {
        JTextField field = new JTextField(20);
        field.setBorder(BorderFactory.createTitledBorder(title));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return field;
    }

    private void editUser(ActionEvent e) {
        // Existing edit logic with updated dialog styling
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            showMessage("Please select a user to edit.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Create styled edit dialog
        JDialog editDialog = new JDialog(this, "Edit User", true);
        editDialog.setLayout(new BorderLayout());
        editDialog.setSize(400, 300);
        editDialog.setLocationRelativeTo(this);
        
        // Add form components with styling
        // ... (rest of edit logic with modern components)
    }

    private void deleteUser(ActionEvent e) {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            showMessage("Please select a user to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int response = JOptionPane.showConfirmDialog(this,
                "<html><div style='width:300px;'>"
                + "Are you sure you want to permanently delete this user?"
                + "</div></html>", 
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        
        if (response == JOptionPane.YES_OPTION) {
            // Existing delete logic
        }
    }

    private void showMessage(String message, String title, int type) {
        JOptionPane.showMessageDialog(this, 
            "<html><div style='width:300px;'>" + message + "</div></html>",
            title,
            type);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new UserManagement().setVisible(true);
        });
    }
}

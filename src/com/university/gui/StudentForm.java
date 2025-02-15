package com.university.gui;

import com.university.database.DAO.StudentDAO;
import com.university.model.Student;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class StudentForm extends JDialog {
    private JTextField txtName, txtEmail;
    private JButton btnSubmit;

    public StudentForm(JFrame parent) {
        super(parent, "Add Student", true);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Form fields
        txtName = new JTextField(20);
        txtEmail = new JTextField(20);
        btnSubmit = new JButton("Submit");

        // Add components
        add(new JLabel("Name:"));
        add(txtName);
        add(new JLabel("Email:"));
        add(txtEmail);
        add(btnSubmit);

        // Submit button action
        btnSubmit.addActionListener((ActionEvent e) -> {
            String name = txtName.getText().trim();
            String email = txtEmail.getText().trim();

            if (!name.isEmpty() && !email.isEmpty()) {
                try {
                    Student student = new Student(name, email);
                    StudentDAO dao = new StudentDAO();
                    dao.addStudent(student);
                    JOptionPane.showMessageDialog(this, "Student added!");
                    dispose(); // Close form
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), 
                        "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Name/Email cannot be empty!");
            }
        });

        pack();
        setLocationRelativeTo(parent);
    }
}

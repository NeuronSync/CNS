package com.university.gui;

import com.university.database.DAO.InstructorDAO;
import com.university.model.Instructor;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class InstructorForm extends JDialog {
    private JTextField txtName, txtEmail;
    private JButton btnSubmit;

    public InstructorForm(JFrame parent) {
        super(parent, "Add Instructor", true);
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
                    // Generate a default personId for the new instructor
                    // In a real scenario, this could be an auto-increment field or fetched from the DB
                    int personId = 0;  // Placeholder value, modify as needed

                    // Create Instructor object with personId, name, and email
                    Instructor instructor = new Instructor(personId, name, email); // Correct constructor usage
                    InstructorDAO dao = new InstructorDAO();
                    dao.addInstructor(instructor);
                    JOptionPane.showMessageDialog(this, "Instructor added!");
                    dispose();
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

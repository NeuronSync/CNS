package com.university.gui;

import javax.swing.*;

public class StudentForm extends JDialog {
    private JTextField txtName, txtEmail;

    public StudentForm(JFrame parent) {
        super(parent, "Add Student", true);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        txtName = new JTextField(20);
        txtEmail = new JTextField(20);
        JButton btnSubmit = new JButton("Submit");

        add(new JLabel("Name:"));
        add(txtName);
        add(new JLabel("Email:"));
        add(txtEmail);
        add(btnSubmit);

        pack();
        setLocationRelativeTo(parent);
    }
}

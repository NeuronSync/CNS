package com.university.gui.instructor;

import com.university.dao.StudentDAO;
import javax.swing.*;
import java.awt.*;

public class ManageStudentGrades extends JFrame {
    private StudentDAO studentDAO = new StudentDAO();

    public ManageStudentGrades(int instructorId) {
        setTitle("Manage Student Grades");
        setSize(500, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        JTextField txtStudentId = new JTextField();
        JTextField txtGrade = new JTextField();
        JButton btnUpdate = new JButton("Update Grade");

        panel.add(new JLabel("Student ID:"));
        panel.add(txtStudentId);
        panel.add(new JLabel("Grade:"));
        panel.add(txtGrade);
        panel.add(btnUpdate);

        btnUpdate.addActionListener(e -> {
            int studentId = Integer.parseInt(txtStudentId.getText());
            double grade = Double.parseDouble(txtGrade.getText());
            boolean success = studentDAO.updateStudentGrade(studentId, grade);

            if (success) {
                JOptionPane.showMessageDialog(this, "Grade updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update grade.");
            }
        });

        add(panel);
    }
}

package com.university.gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import com.university.database.DAO.EnrollmentDAO;
import com.university.model.Enrollment;
import java.sql.SQLException;

public class GradeForm extends JDialog {
    private JComboBox<Enrollment> enrollmentCombo;
    private JTextField txtGrade;
    private JButton btnSubmit;

    public GradeForm(JFrame parent) {
        super(parent, "Update Grade", true);
        setLayout(new GridLayout(3, 2));

        // Fetch all enrollments (with or without grades)
        EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
        List<Enrollment> enrollments;
        try {
            enrollments = enrollmentDAO.getAllEnrollments();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching enrollments: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            enrollments = List.of(); // Empty list to avoid issues
        }

        enrollmentCombo = new JComboBox<>(enrollments.toArray(new Enrollment[0]));
        enrollmentCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Enrollment) {
                    Enrollment e = (Enrollment) value;
                    String gradeInfo = (e.getGrade() >= 0) ? " (Grade: " + e.getGrade() + ")" : " (No Grade)";
                    setText(e.getStudent().getName() + " - " + e.getCourse().getCourseName() + gradeInfo);
                }
                return this;
            }
        });

        txtGrade = new JTextField(5);
        btnSubmit = new JButton("Update Grade");

        // Update grade field when enrollment is selected
        enrollmentCombo.addActionListener(e -> {
            Enrollment selected = (Enrollment) enrollmentCombo.getSelectedItem();
            if (selected != null) {
                txtGrade.setText(selected.getGrade() >= 0 ? String.valueOf(selected.getGrade()) : "");
            }
        });

        // Add components
        add(new JLabel("Enrollment:"));
        add(enrollmentCombo);
        add(new JLabel("Grade (0-100):"));
        add(txtGrade);
        add(new JLabel());
        add(btnSubmit);

        // Submit action
        btnSubmit.addActionListener(e -> {
            Enrollment enrollment = (Enrollment) enrollmentCombo.getSelectedItem();
            if (enrollment == null) {
                JOptionPane.showMessageDialog(this, "Please select an enrollment!", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String gradeText = txtGrade.getText().trim();
            try {
                double grade = Double.parseDouble(gradeText);
                if (grade < 0 || grade > 100) {
                    throw new NumberFormatException();
                }
                enrollmentDAO.updateGrade(enrollment.getEnrollmentId(), grade);
                JOptionPane.showMessageDialog(this, "Grade updated!");
                dispose();
            } catch (NumberFormatException | SQLException ex) {
                JOptionPane.showMessageDialog(this, "Invalid grade! Enter a value between 0-100.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        pack();
        setLocationRelativeTo(parent);
    }
}


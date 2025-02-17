package com.university.gui;

import com.university.database.DAO.EnrollmentDAO;
import com.university.model.Enrollment;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class GradeForm extends JDialog {
    private JComboBox<Enrollment> enrollmentCombo;
    private JTextField txtGrade;
    private JButton btnSubmit;

    public GradeForm(JFrame parent) {
        super(parent, "Assign Grade", true);
        setLayout(new GridLayout(3, 2));

        // Fetch enrollments without grades
        EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
        List<Enrollment> enrollments = null;
        
        try {
            enrollments = enrollmentDAO.getEnrollmentsWithoutGrades(); // This can throw SQLException
        } catch (SQLException e) {
            // Handle SQLException and display an error message to the user
            JOptionPane.showMessageDialog(this, "Error fetching enrollments: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        // Populate the combo box with the fetched enrollments
        enrollmentCombo = new JComboBox<>(enrollments.toArray(new Enrollment[0]));
        enrollmentCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Enrollment) {
                    Enrollment e = (Enrollment) value;
                    setText(e.getStudent().getName() + " - " + e.getCourse().getCourseName());
                }
                return this;
            }
        });

        txtGrade = new JTextField(5);
        btnSubmit = new JButton("Assign Grade");

        add(new JLabel("Enrollment:"));
        add(enrollmentCombo);
        add(new JLabel("Grade (0-100):"));
        add(txtGrade);
        add(new JLabel());
        add(btnSubmit);

        // Submit action
        btnSubmit.addActionListener(e -> {
            Enrollment enrollment = (Enrollment) enrollmentCombo.getSelectedItem();
            String gradeText = txtGrade.getText().trim();
            
            try {
                double grade = Double.parseDouble(gradeText);
                if (grade < 0 || grade > 100) {
                    throw new NumberFormatException();
                }
                enrollmentDAO.updateGrade(enrollment.getEnrollmentId(), grade);
                JOptionPane.showMessageDialog(this, "Grade assigned!");
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid grade! Enter a value between 0-100.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error updating grade: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        pack();
        setLocationRelativeTo(parent);
    }
}


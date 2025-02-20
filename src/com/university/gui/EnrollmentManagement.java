package com.university.gui.admin;

import com.university.dao.EnrollmentDAO;
import com.university.models.Enrollment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class EnrollmentManagement extends JFrame {
    private EnrollmentDAO enrollmentDAO;
    private JTable table;
    private DefaultTableModel model;

    public EnrollmentManagement() {
        enrollmentDAO = new EnrollmentDAO();

        setTitle("Enrollment Management");
        setSize(700, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // Table for displaying enrollments
        model = new DefaultTableModel(new String[]{"ID", "Student Email", "Course", "Enrollment Date", "Grade"}, 0);
        table = new JTable(model);
        loadEnrollments();

        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton enrollButton = new JButton("Enroll Student");
        JButton modifyButton = new JButton("Modify Enrollment");
        JButton deleteButton = new JButton("Delete Enrollment");

        buttonPanel.add(enrollButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        enrollButton.addActionListener(this::enrollStudent);
        modifyButton.addActionListener(this::modifyEnrollment);
        deleteButton.addActionListener(this::deleteEnrollment);

        add(panel);
    }

    // Load all enrollments into the table
    private void loadEnrollments() {
        model.setRowCount(0);
        List<Enrollment> enrollments = enrollmentDAO.getAllEnrollments();
        for (Enrollment enrollment : enrollments) {
            model.addRow(new Object[]{
                enrollment.getEnrollmentId(),
                enrollment.getStudentEmail(),
                enrollment.getCourseName(),
                enrollment.getEnrollmentDate(),
                enrollment.getGrade()
            });
        }
    }

    // Enroll Student
    private void enrollStudent(ActionEvent e) {
        int studentId = Integer.parseInt(JOptionPane.showInputDialog("Enter Student ID:"));
        int courseId = Integer.parseInt(JOptionPane.showInputDialog("Enter Course ID:"));

        if (enrollmentDAO.enrollStudent(studentId, courseId)) {
            loadEnrollments();
        } else {
            JOptionPane.showMessageDialog(this, "Error enrolling student.");
        }
    }

    // Modify Enrollment
    private void modifyEnrollment(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select an enrollment first.");
            return;
        }

        int enrollmentId = (int) table.getValueAt(selectedRow, 0);
        int newCourseId = Integer.parseInt(JOptionPane.showInputDialog("Enter New Course ID:"));

        if (enrollmentDAO.updateEnrollment(enrollmentId, newCourseId)) {
            loadEnrollments();
        } else {
            JOptionPane.showMessageDialog(this, "Error modifying enrollment.");
        }
    }

    // Delete Enrollment
    private void deleteEnrollment(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) return;

        int enrollmentId = (int) table.getValueAt(selectedRow, 0);
        if (enrollmentDAO.deleteEnrollment(enrollmentId)) {
            loadEnrollments();
        } else {
            JOptionPane.showMessageDialog(this, "Error deleting enrollment.");
        }
    }
}

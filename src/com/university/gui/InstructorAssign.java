package com.university.gui.admin;

import com.university.dao.InstructorAssignmentDAO;
import com.university.models.InstructorAssignment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class InstructorAssign extends JFrame {
    private InstructorAssignmentDAO assignmentDAO;
    private JTable table;
    private DefaultTableModel model;

    public InstructorAssign() {
        assignmentDAO = new InstructorAssignmentDAO();

        setTitle("Instructor Assignment");
        setSize(700, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // Table for displaying assignments
        model = new DefaultTableModel(new String[]{"ID", "Instructor Email", "Course", "Assigned Date"}, 0);
        table = new JTable(model);
        loadAssignments();

        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton assignButton = new JButton("Assign Instructor");
        JButton reassignButton = new JButton("Reassign Instructor");
        JButton removeButton = new JButton("Remove Assignment");

        buttonPanel.add(assignButton);
        buttonPanel.add(reassignButton);
        buttonPanel.add(removeButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        assignButton.addActionListener(this::assignInstructor);
        reassignButton.addActionListener(this::reassignInstructor);
        removeButton.addActionListener(this::removeAssignment);

        add(panel);
    }

    // Load all instructor assignments into the table
    private void loadAssignments() {
        model.setRowCount(0);
        List<InstructorAssignment> assignments = assignmentDAO.getAllAssignments();
        for (InstructorAssignment assignment : assignments) {
            model.addRow(new Object[]{
                assignment.getAssignmentId(),
                assignment.getInstructorEmail(),
                assignment.getCourseName(),
                assignment.getAssignedDate()
            });
        }
    }

    // Assign Instructor
    private void assignInstructor(ActionEvent e) {
        int instructorId = Integer.parseInt(JOptionPane.showInputDialog("Enter Instructor ID:"));
        int courseId = Integer.parseInt(JOptionPane.showInputDialog("Enter Course ID:"));

        if (assignmentDAO.assignInstructor(instructorId, courseId)) {
            loadAssignments();
        } else {
            JOptionPane.showMessageDialog(this, "Error assigning instructor.");
        }
    }

    // Reassign Instructor
    private void reassignInstructor(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select an assignment first.");
            return;
        }

        int assignmentId = (int) table.getValueAt(selectedRow, 0);
        int newCourseId = Integer.parseInt(JOptionPane.showInputDialog("Enter New Course ID:"));

        if (assignmentDAO.reassignInstructor(assignmentId, newCourseId)) {
            loadAssignments();
        } else {
            JOptionPane.showMessageDialog(this, "Error reassigning instructor.");
        }
    }

    // Remove Assignment
    private void removeAssignment(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) return;

        int assignmentId = (int) table.getValueAt(selectedRow, 0);
        if (assignmentDAO.removeInstructorAssignment(assignmentId)) {
            loadAssignments();
        } else {
            JOptionPane.showMessageDialog(this, "Error removing assignment.");
        }
    }
}

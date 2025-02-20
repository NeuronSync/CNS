package com.university.gui.admin;

import com.university.dao.CourseDAO;
import com.university.models.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class CourseManagement extends JFrame {
    private CourseDAO courseDAO;
    private JTable table;
    private DefaultTableModel model;

    public CourseManagement() {
        courseDAO = new CourseDAO();

        setTitle("Course Management");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // Table for displaying courses
        model = new DefaultTableModel(new String[]{"ID", "Name", "Code", "Department", "Credits"}, 0);
        table = new JTable(model);
        loadCourses();

        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Course");
        JButton editButton = new JButton("Edit Course");
        JButton deleteButton = new JButton("Delete Course");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        addButton.addActionListener(this::addCourse);
        editButton.addActionListener(this::editCourse);
        deleteButton.addActionListener(this::deleteCourse);

        add(panel);
    }

    // Load all courses into the table
    private void loadCourses() {
        model.setRowCount(0);
        List<Course> courses = courseDAO.getAllCourses();
        for (Course course : courses) {
            model.addRow(new Object[]{course.getCourseId(), course.getCourseName(), course.getCourseCode(), course.getDepartment(), course.getCredits()});
        }
    }

    // Add Course
    private void addCourse(ActionEvent e) {
        String name = JOptionPane.showInputDialog("Course Name:");
        String code = JOptionPane.showInputDialog("Course Code:");
        String department = JOptionPane.showInputDialog("Department:");  // ✅ Added missing department
        String description = JOptionPane.showInputDialog("Description:");
        int credits;

        try {
            credits = Integer.parseInt(JOptionPane.showInputDialog("Credits:"));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input for credits.");
            return;
        }

        if (courseDAO.addCourse(name, code, description, department, credits)) {
            loadCourses();
        } else {
            JOptionPane.showMessageDialog(this, "Error adding course.");
        }
    }

    // Edit Course
    private void editCourse(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a course first.");
            return;
        }

        int courseId = (int) table.getValueAt(selectedRow, 0);
        String name = JOptionPane.showInputDialog("New Course Name:", table.getValueAt(selectedRow, 1));
        String code = JOptionPane.showInputDialog("New Course Code:", table.getValueAt(selectedRow, 2));
        String department = JOptionPane.showInputDialog("New Department:", table.getValueAt(selectedRow, 3)); // ✅ Added department
        String description = JOptionPane.showInputDialog("New Description:");
        int credits;

        try {
            credits = Integer.parseInt(JOptionPane.showInputDialog("New Credits:", table.getValueAt(selectedRow, 4)));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input for credits.");
            return;
        }

        if (courseDAO.updateCourse(courseId, name, code, description, department, credits)) {
            loadCourses();
        } else {
            JOptionPane.showMessageDialog(this, "Error updating course.");
        }
    }

    // Delete Course
    private void deleteCourse(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) return;

        int courseId = (int) table.getValueAt(selectedRow, 0);
        if (courseDAO.deleteCourse(courseId)) {
            loadCourses();
        } else {
            JOptionPane.showMessageDialog(this, "Error deleting course.");
        }
    }
}

package com.university.gui.instructor;

import com.university.dao.InstructorDAO;
import com.university.models.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewAssignedCourses extends JFrame {
    private InstructorDAO instructorDAO = new InstructorDAO();
    private JTable courseTable;
    private DefaultTableModel tableModel;

    public ViewAssignedCourses(int instructorId) {
        setTitle("Assigned Courses");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = {"Course ID", "Course Name", "Department", "Semester", "Credits"};
        tableModel = new DefaultTableModel(columnNames, 0);
        courseTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(courseTable);
        add(scrollPane, BorderLayout.CENTER);

        loadCourses(instructorId);
    }

    private void loadCourses(int instructorId) {
        tableModel.setRowCount(0);
        List<Course> courses = instructorDAO.getAssignedCourses(instructorId);
        for (Course course : courses) {
            tableModel.addRow(new Object[]{
                course.getCourseId(),
                course.getCourseName(),
                course.getDepartment(),
                course.getSemester(),
                course.getCredits()
            });
        }
    }
}

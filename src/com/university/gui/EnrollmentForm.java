package com.university.gui;

import com.university.database.DAO.CourseDAO;
import com.university.database.DAO.EnrollmentDAO;
import com.university.database.DAO.StudentDAO;
import com.university.model.Student;
import com.university.model.Course;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class EnrollmentForm extends JDialog {
    private JComboBox<Student> studentCombo;
    private JComboBox<Course> courseCombo;
    private JButton btnEnroll;

    public EnrollmentForm(JFrame parent) {
        super(parent, "Enroll Student", true);
        setLayout(new GridLayout(3, 2));

        // Fetch students and courses from DB with exception handling
        StudentDAO studentDAO = new StudentDAO();
        CourseDAO courseDAO = new CourseDAO();
        List<Student> students;
        List<Course> courses;
        try {
            students = studentDAO.getAllStudents();
            courses = courseDAO.getAllCourses();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error fetching data: " + ex.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
            // Initialize with empty lists if there's an error
            students = new ArrayList<>();
            courses = new ArrayList<>();
        }

        // Student dropdown
        studentCombo = new JComboBox<>(students.toArray(new Student[0]));
        studentCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Student) {
                    setText(((Student) value).getName());
                }
                return this;
            }
        });

        // Course dropdown
        courseCombo = new JComboBox<>(courses.toArray(new Course[0]));
        courseCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Course) {
                    setText(((Course) value).getCourseName());
                }
                return this;
            }
        });

        btnEnroll = new JButton("Enroll");

        // Add components
        add(new JLabel("Student:"));
        add(studentCombo);
        add(new JLabel("Course:"));
        add(courseCombo);
        add(new JLabel());
        add(btnEnroll);

        // Enroll button action
        btnEnroll.addActionListener(e -> {
            Student student = (Student) studentCombo.getSelectedItem();
            Course course = (Course) courseCombo.getSelectedItem();

            if (student != null && course != null) {
                try {
                    EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
                    enrollmentDAO.enrollStudent(student, course);
                    JOptionPane.showMessageDialog(this, "Enrollment successful!");
                    dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), 
                        "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        pack();
        setLocationRelativeTo(parent);
    }
}


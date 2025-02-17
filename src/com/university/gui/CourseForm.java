package com.university.gui;

import com.university.database.DAO.CourseDAO;
import com.university.database.DAO.InstructorDAO;
import com.university.model.Course;
import com.university.model.Instructor;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class CourseForm extends JDialog {
    private JTextField txtCourseName, txtCredits;
    private JComboBox<Instructor> instructorCombo;
    private JButton btnSubmit;

    public CourseForm(JFrame parent) {
        super(parent, "Add Course", true);
        setLayout(new GridLayout(4, 2));

        // Fetch instructors from DB
        InstructorDAO instructorDAO = new InstructorDAO();
        List<Instructor> instructors = null;
        try {
            instructors = instructorDAO.getAllInstructors();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error fetching instructors: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            return;  // Exit the method early if there's an error fetching instructors
        }

        // Form fields
        txtCourseName = new JTextField(20);
        txtCredits = new JTextField(5);
        instructorCombo = new JComboBox<>(instructors.toArray(new Instructor[0]));
        instructorCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                           boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Instructor) {
                    setText(((Instructor) value).getName());
                }
                return this;
            }
        });

        btnSubmit = new JButton("Submit");

        // Add components
        add(new JLabel("Course Name:"));
        add(txtCourseName);
        add(new JLabel("Credits:"));
        add(txtCredits);
        add(new JLabel("Instructor:"));
        add(instructorCombo);
        add(new JLabel());
        add(btnSubmit);

        // Submit action
        btnSubmit.addActionListener(e -> {
            String courseName = txtCourseName.getText().trim();
            String creditsText = txtCredits.getText().trim();
            Instructor instructor = (Instructor) instructorCombo.getSelectedItem();

            if (!courseName.isEmpty() && !creditsText.isEmpty() && instructor != null) {
                try {
                    int credits = Integer.parseInt(creditsText);
                    Course course = new Course(courseName, credits, instructor); // Using the constructor that takes Instructor
                    CourseDAO dao = new CourseDAO();
                    dao.addCourse(course);
                    JOptionPane.showMessageDialog(this, "Course added!");
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid credits! Enter a number.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
                            "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "All fields are required!");
            }
        });

        pack();
        setLocationRelativeTo(parent);
    }
}


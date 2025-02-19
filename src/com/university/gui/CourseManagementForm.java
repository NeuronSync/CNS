package com.university.gui;

import com.university.database.DAO.CourseDAO;
import com.university.database.DAO.InstructorDAO;
import com.university.model.Course;
import com.university.model.Instructor;
import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class CourseManagementForm extends JDialog {
    private JComboBox<Course> courseCombo;
    private JComboBox<Instructor> instructorCombo;

    public CourseManagementForm(JFrame parent) {
        super(parent, "Manage Course Instructors", true);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setSize(400, 200);

        try {
            List<Course> courses = new CourseDAO().getAllCourses();
            List<Instructor> instructors = new InstructorDAO().getAllInstructors();

            courseCombo = new JComboBox<>(courses.toArray(new Course[0]));
            instructorCombo = new JComboBox<>(instructors.toArray(new Instructor[0]));

            add(new JLabel("Select Course:"));
            add(courseCombo);
            add(new JLabel("Assign New Instructor:"));
            add(instructorCombo);

            JButton btnUpdate = new JButton("Update Instructor");
            btnUpdate.addActionListener(e -> updateInstructor());
            add(btnUpdate);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage());
        }

        setLocationRelativeTo(parent);
    }

    private void updateInstructor() {
        Course selectedCourse = (Course) courseCombo.getSelectedItem();
        Instructor selectedInstructor = (Instructor) instructorCombo.getSelectedItem();

        try {
            new CourseDAO().updateCourseInstructor(
                selectedCourse.getCourseId(),
                selectedInstructor.getPersonId()
            );
            JOptionPane.showMessageDialog(this, "Instructor updated successfully!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Update failed: " + ex.getMessage());
        }
    }
}

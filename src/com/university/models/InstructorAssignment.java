package com.university.models;

import java.sql.Timestamp;

public class InstructorAssignment {
    private int assignmentId;
    private String instructorEmail;
    private String courseName;
    private Timestamp assignedDate;

    public InstructorAssignment(int assignmentId, String instructorEmail, String courseName, Timestamp assignedDate) {
        this.assignmentId = assignmentId;
        this.instructorEmail = instructorEmail;
        this.courseName = courseName;
        this.assignedDate = assignedDate;
    }

    // Getters and Setters
    public int getAssignmentId() { return assignmentId; }
    public void setAssignmentId(int assignmentId) { this.assignmentId = assignmentId; }

    public String getInstructorEmail() { return instructorEmail; }
    public void setInstructorEmail(String instructorEmail) { this.instructorEmail = instructorEmail; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public Timestamp getAssignedDate() { return assignedDate; }
    public void setAssignedDate(Timestamp assignedDate) { this.assignedDate = assignedDate; }
}

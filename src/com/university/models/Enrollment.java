package com.university.models;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Enrollment {
    private int enrollmentId;
    private String studentEmail;
    private String courseName;
    private Timestamp enrollmentDate;
    private BigDecimal grade;

    public Enrollment(int enrollmentId, String studentEmail, String courseName, Timestamp enrollmentDate, BigDecimal grade) {
        this.enrollmentId = enrollmentId;
        this.studentEmail = studentEmail;
        this.courseName = courseName;
        this.enrollmentDate = enrollmentDate;
        this.grade = grade;
    }

    // Getters and Setters
    public int getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(int enrollmentId) { this.enrollmentId = enrollmentId; }

    public String getStudentEmail() { return studentEmail; }
    public void setStudentEmail(String studentEmail) { this.studentEmail = studentEmail; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public Timestamp getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(Timestamp enrollmentDate) { this.enrollmentDate = enrollmentDate; }

    public BigDecimal getGrade() { return grade; }
    public void setGrade(BigDecimal grade) { this.grade = grade; }
}

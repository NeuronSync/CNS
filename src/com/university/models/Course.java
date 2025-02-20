package com.university.models;

public class Course {
    private int courseId;
    private String courseName;
    private String courseCode;
    private String department;
    private String semester;
    private int credits;

    public Course(int courseId, String courseName, String courseCode, String department, String semester, int credits) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.department = department;
        this.semester = semester;
        this.credits = credits;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getDepartment() {
        return department;
    }

    public String getSemester() {
        return semester;
    }

    public int getCredits() {
        return credits;
    }
}

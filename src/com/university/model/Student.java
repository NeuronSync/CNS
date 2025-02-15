package com.university.model;

public class Student extends Person {
    public Student(String name, String email) {
        super(name, email);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public void displayDetails() {
        System.out.println("Student: " + name + " | Email: " + email);
    }
}

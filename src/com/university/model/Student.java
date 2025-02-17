package com.university.model;

public class Student extends Person {
    private int personId;

    // Primary constructor that requires a personId
    public Student(int personId, String name, String email) {
        super(personId, name, email, "student");  // pass 'student' as the role
        this.personId = personId;
    }

    // Overloaded constructor for backwards compatibility (defaults personId to 0)
    public Student(String name, String email) {
        this(0, name, email);  // Default personId to 0
    }

    // Getter for personId (used by DAO)
    public int getPersonId() {
        return personId;
    }

    // Setter for personId, if needed
    public void setPersonId(int personId) {
        this.personId = personId;
    }

    @Override
    public void displayDetails() {
        System.out.println("Student: " + getName() + " | Email: " + getEmail());
    }
}


package com.university.model;

public class Student extends Person {
    private int personId;

    // Primary constructor that requires a personId
    public Student(int personId, String name, String email) {
        super(name, email);
        this.personId = personId;
    }

    // Overloaded constructor for backwards compatibility (defaults personId to 0)
    public Student(String name, String email) {
        this(0, name, email);
    }

    // Getter for personId (used by DAO)
    public int getPersonId() {
        return personId;
    }

    // Setter for personId, if needed
    public void setPersonId(int personId) {
        this.personId = personId;
    }

    // Provide access to name (inherited from Person)
    public String getName() {
        return name;
    }

    // Provide access to email (inherited from Person)
    public String getEmail() {
        return email;
    }

    @Override
    public void displayDetails() {
        System.out.println("Student: " + name + " | Email: " + email);
    }
}


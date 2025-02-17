package com.university.model;

public class Person {
    private int personId;
    private String name;
    private String email;
    private String role;

    public Person(int personId, String name, String email, String role) {
        this.personId = personId;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    // Getters for Person class
    public int getPersonId() {
        return personId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public void displayDetails() {
        // Display details based on role, can be overridden by subclasses
        System.out.println("Person: " + name + " | Email: " + email + " | Role: " + role);
    }
}


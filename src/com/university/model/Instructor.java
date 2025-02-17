package com.university.model;

public class Instructor extends Person {

    public Instructor(int personId, String name, String email) {
        super(personId, name, email, "instructor");
    }

    @Override
    public void displayDetails() {
        System.out.println("Instructor: " + getName() + " | Email: " + getEmail());
    }
}


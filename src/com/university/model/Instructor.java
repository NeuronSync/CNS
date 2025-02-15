package com.university.model;

public class Instructor extends Person {
    public Instructor(String name, String email) {
        super(name, email);
    }

    @Override
    public void displayDetails() {
        System.out.println("Instructor: " + name + " | Email: " + email);
    }
}

package com.university.models;

public class User {
    private int userId;
    private String username;
    private String role;
    private int personId;

    public User(int userId, String username, String role, int personId) {
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.personId = personId;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public int getPersonId() {
        return personId;
    }
}

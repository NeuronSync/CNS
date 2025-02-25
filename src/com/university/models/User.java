package com.university.models;

import java.sql.Timestamp;

public class User {
    private int userId;
    private String email;
    private String username;
    private String role;
    private int failedAttempts;
    private Timestamp lockedUntil;

    public User(int userId, String email, String username, String role, int failedAttempts, Timestamp lockedUntil) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.role = role;
        this.failedAttempts = failedAttempts;
        this.lockedUntil = lockedUntil;
    }

    public int getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public Timestamp getLockedUntil() {
        return lockedUntil;
    }
}

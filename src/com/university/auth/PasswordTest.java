package com.university.auth;

public class PasswordTest {
    public static void main(String[] args) {
        String password = "admin123";
        String hashed = BCryptUtil.hashPassword(password);

        System.out.println("New Hashed Password: " + hashed);
    }
}


-- Create database
CREATE DATABASE IF NOT EXISTS university_db;
USE university_db;

-- Person table (base for students/instructors)
CREATE TABLE Person (
    person_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role ENUM('student', 'instructor') NOT NULL
);

-- Courses table
CREATE TABLE Course (
    course_id INT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL,
    credits INT NOT NULL,
    instructor_id INT,
    FOREIGN KEY (instructor_id) REFERENCES Person(person_id)
);

-- Enrollments table
CREATE TABLE Enrollment (
    enrollment_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    grade DECIMAL(5,2),
    FOREIGN KEY (student_id) REFERENCES Person(person_id),
    FOREIGN KEY (course_id) REFERENCES Course(course_id)
);

-- Add users table for authentication
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL
);

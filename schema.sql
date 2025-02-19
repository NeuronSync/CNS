CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('admin', 'instructor', 'student') NOT NULL,
    person_id INT,
    FOREIGN KEY (person_id) REFERENCES Person(person_id)
);

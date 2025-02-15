USE university_db;

-- Insert instructors
INSERT INTO Person (name, email, role) VALUES
('Dr. Mageto', 'mageto@uni.com', 'instructor'),
('Prof. Lesley', 'lesley@uni.com', 'instructor');

-- Insert students
INSERT INTO Person (name, email, role) VALUES
('Alboss', 'alboss@uni.com', 'student'),
('Lenox', 'lenox@uni.com', 'student');

-- Insert courses
INSERT INTO Course (course_name, credits, instructor_id) VALUES
('Java Programming', 3, 1),
('Network Security', 4, 2);

-- Enroll students
INSERT INTO Enrollment (student_id, course_id, grade) VALUES
(3, 1, 85.5),  -- Alboss in Java Programming
(4, 2, 92.0);   -- Lenox in Network Security

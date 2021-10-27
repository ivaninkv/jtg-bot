CREATE DATABASE University;
USE University;

CREATE TABLE Student (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(30),
    Last_Name VARCHAR(30),
    E_mail VARCHAR(30) UNIQUE,
    Rate INT CHECK (Rate >= 1 AND Rate <= 6)
);

CREATE TABLE Book (
    Id INT AUTO_INCREMENT,
    Student_id INT,
    Title VARCHAR(50),
    CONSTRAINT PK_Book PRIMARY KEY (Id, Title),
    FOREIGN KEY (Student_id) REFERENCES Student(Id)
);

CREATE TABLE Teacher (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(30),
    Last_Name VARCHAR(30),
    E_mail VARCHAR(30) UNIQUE,
    Subject VARCHAR(30)
);

CREATE TABLE StudentTeacherLink (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    Student_id INT,
    Teacher_id INT,
    FOREIGN KEY (Student_id) REFERENCES Student(Id),
    FOREIGN KEY (Teacher_id) REFERENCES Teacher(Id),
    UNIQUE (Student_id, Teacher_id)
);

INSERT INTO Student (Name, Last_Name, E_mail, Rate) VALUES
    ('Ivan', 'Ivanov', 'ivan@ivanov.ru', 1),
    ('Petr', 'Petrov', 'petr@petrov.ru', 1),
    ('Semen', 'Ivanov', 'semen@ivanov.ru', 1),
    ('Artem', 'Sorokin', 'artem@sorokin.ru', 1),
    ('Ivan', 'Petrov', 'ivan@petrov.ru', 1);

INSERT INTO Book (Student_id, Title) VALUES
    (1, 'The Book of Eli'),
    (1, 'Harry Potter 1'),
    (2, 'Harry Potter 2'),
    (2, 'Harry Potter 3'),
    (3, 'Harry Potter 4'),
    (3, 'The Chronicles of Amber'),
    (3, 'The Hobbit, or There and Back Again'),
    (4, 'The Silmarillion'),
    (4, 'A Game of Thrones'),
    (4, '1984');

INSERT INTO Teacher (Name, Last_Name, E_mail, Subject) VALUES
    ('Dmitry', 'Mendeleev', 'dm@men.ru', 'Chemistry'),
    ('Albert', 'Einstein', 'a@e.ru', 'Physic'),
    ('Galileo', 'Galilei', 'g@g.ru', 'Physic'),
    ('Pythagoras', 'of Samos', 'p@s.ru', 'Mathematics');

INSERT INTO StudentTeacherLink (Student_id, Teacher_id) VALUES
    (1, 1),
    (2, 1),
    (2, 2),
    (2, 3),
    (3, 1),
    (3, 4),
    (4, 2),
    (5, 1);

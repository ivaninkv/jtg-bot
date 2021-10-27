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
    Id INT,
    Student_id INT,
    Title VARCHAR(30),
    CONSTRAINT PK_Book PRIMARY KEY (Id, Title),
    FOREIGN KEY (Student_id) REFERENCES Student(Id)
);

CREATE TABLE Teacher (
    Id INT PRIMARY KEY,
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

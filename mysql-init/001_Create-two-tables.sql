CREATE DATABASE cities;
USE cities;

CREATE TABLE country (
    id INT PRIMARY KEY,
    name VARCHAR(30),
    population INT );

CREATE TABLE city (
    id INT,
    name VARCHAR(30),
    country_id INT,
    population INT,
    FOREIGN KEY (country_id)
        REFERENCES country(id) );


INSERT INTO country (id, name, population) VALUES (1, 'Ukraine', 41806221);
INSERT INTO country (id, name, population) VALUES (2, 'Russia', 146171015);
INSERT INTO country (id, name, population) VALUES (3, 'Belorus', 9349645);
INSERT INTO country (id, name, population) VALUES (4, 'Moldova', 2640438);

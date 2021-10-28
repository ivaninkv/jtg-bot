\c cities;

CREATE TABLE country (
    id SERIAL PRIMARY KEY,
    name VARCHAR(30),
    population INT );

CREATE TABLE city (
    id SERIAL PRIMARY KEY,
    name VARCHAR(30),
    country_id INT REFERENCES country,
    population INT );

INSERT INTO country (id, name, population) VALUES
    (1, 'Ukraine', 41806221),
    (2, 'Russia', 146171015),
    (3, 'Belorus', 9349645),
    (4, 'Moldova', 2640438);

INSERT INTO city (name, country_id, population) VALUES
    ('Kharkov', 1, 1443000),
    ('Kyiv', 1, 3703100),
    ('Minsk', 3, 2545500),
    ('Odessa', 1, 1017699),
    ('Voronezh', 2, 1058261),
    ('Kishinev', 4, 695400);

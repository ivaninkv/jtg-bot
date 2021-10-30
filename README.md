# Java Telegram Bot

Телеграм бот по мотивам [цикла статей](https://javarush.ru/groups/posts/2935-java-proekt-ot-a-do-ja-pishem-realjhnihy-proekt-dlja-portfolio) на [https://javarush.ru/](https://javarush.ru/).


## Домашнее задание 1

Ссылка на [статью](https://javarush.ru/groups/posts/2946-java-proekt-ot-a-do-ja-razbiraem-bazih-dannihkh-i-jazihk-sql).

Чтобы добавить интереса к этой серии статей на JRTB, время от времени будут домашние задания. Например, без сегодняшнего задания следующую статью уже будет значительно сложнее понять, ведь там будет много практики.

Поэтому задание: установить СУБД MySQL себе на компьютер и войти в базу данных или через консоль, или через другие решения.

Всем спасибо за прочтение, до скорых встреч!

---

В рамках ДЗ1 был добавлен файл `compose.yml`, который поднимает в контейнере `mysql` и `adminer` в качестве админки к нему. Документацию по написанию `compose`-файлов можно найти [тут](https://docs.docker.com/compose/compose-file/compose-file-v3/).


## Домашнее задание 2

Ссылка на [статью](https://javarush.ru/groups/posts/2957-java-proekt-ot-a-do-ja-razbiraem-bazih-dannihkh-i-jazihk-sql-chastjh-2).

Домашнее задание будет следующим:
1. Нужно добавить в схему таблицы `country` первичный ключ (PRIMARY KEY) из поля `ID`.
2. Добавить в таблицу `country` еще одну страну — Молдову.
3. По схеме предыдущей статьи создать таблицу `city`, в которой будут все описанные поля. Имена полей будут следующими: `id`, `name`, `country_id`, `population`.
4. Добавить первичный ключ в таблице `city`.
5. Добавить внешний ключ в таблице `city`.

Задание интересное, для тех, кто в теме, оно будет быстрое и несложное. Для тех, кто нет, — послужит отличным закреплением материала из этой статьи.

---

Для начала привожу команды из статьи, чтобы привести БД к нужному состоянию:
```sql
CREATE DATABASE cities;
USE cities;
CREATE TABLE country (id INT, name VARCHAR(30));
INSERT INTO country VALUES (1, 'Ukraine');
INSERT INTO country VALUES (2, 'Russia');
INSERT INTO country VALUES (3, 'Belorus');
ALTER TABLE country ADD COLUMN population INT;
UPDATE country SET population = 41806221 WHERE id = 1;
UPDATE country SET population = 146171015 WHERE id = 2;
UPDATE country SET population = 9349645 WHERE id = 3;
INSERT INTO country (name, population) VALUES ('Georgia', 1234567);
DELETE FROM country WHERE name = 'Georgia';
```

Теперь, собственно, сами команды для ДЗ:
```sql
ALTER TABLE country ADD PRIMARY KEY (id);
INSERT INTO country (id, name, population) VALUES (4, 'Moldova', 2640438);
CREATE TABLE city (id INT, name VARCHAR(30), country_id INT, population INT);
ALTER TABLE city ADD PRIMARY KEY (id);
ALTER TABLE city ADD FOREIGN KEY (country_id) REFERENCES country(id);
```

Также написан init скрипт `001_Create-two-tables.sql` для `docker-compose`, приводящий БД в состояние по завершению этого ДЗ.


## Домашнее задание 3

Ссылка на [статью](https://javarush.ru/groups/posts/2977-java-proekt-ot-a-do-ja-razbiraem-bazih-dannihkh-i-jazihk-sql-chastjh-3).

Написать запросы на получение по нашим данным:
1. самой мало- / многочисленной страны;
2. среднее количество жителей в стране;
3. среднее количество жителей в странах, чьи имена заканчиваются на `a`;
4. количество стран, у которых население больше четырех миллионов;
5. отсортировать страны по уменьшению количества жителей;
6. отсортировать страны по имени в натуральном порядке.

---

```sql
-- 1. самой мало- / многочисленной страны;
SELECT *
FROM country
WHERE population = (
                     SELECT min(population)
                     FROM country
                   );

SELECT *
FROM country
WHERE population = (
                     SELECT max(population)
                     FROM country
                   );

-- 2. среднее количество жителей в стране;
SELECT country_id
    , AVG(population) avgPopulation
FROM city
GROUP BY country_id;

-- 3. среднее количество жителей в странах, чьи имена заканчиваются на `a`;
SELECT city.country_id
    , AVG(city.population) avgPopulation
FROM city
JOIN country on country.id = city.country_id
    AND country.name LIKE '%a'
GROUP BY city.country_id;

-- 4. количество стран, у которых население больше четырех миллионов;
SELECT count(id)
FROM country
WHERE population > 4000000;

-- 5. отсортировать страны по уменьшению количества жителей;
SELECT *
FROM country
ORDER BY population DESC;

-- 6. отсортировать страны по имени в натуральном порядке.
SELECT *
FROM country
ORDER BY name;
```

## Домашнее задание 4

Ссылка на [статью](https://javarush.ru/groups/posts/3008-java-proekt-ot-a-do-ja-razbiraem-bazih-dannihkh-i-jazihk-sql-chastjh-5--svjazi-i-dzhoinih).

1. Написать SQL script создания таблицы `Student` с полями: id (primary key), name, last_name, e_mail (unique).
2. Написать SQL script создания таблицы `Book` с полями: id, title (id + title = primary key). Связать `Student` и `Book` связью `Student` one-to-many `Book`.
3. Написать SQL script создания таблицы `Teacher` с полями: id (primary key), name, last_name, e_mail (unique), subject.
4. Связать `Student` и `Teacher` связью `Student` many-to-many `Teacher`.
5. Выбрать `Student` у которых в фамилии есть `oro`, например `Sidorov`, `Voronovsky`.
6. Выбрать из таблицы `Student` все фамилии (`last_name`) и количество их повторений. Считать, что в базе есть однофамильцы. Отсортировать по количеству в порядке убывания.
7. Выбрать из `Student` топ 3 самых повторяющихся имен `name`. Отсортировать по количеству в порядке убывания.
8. Выбрать `Student`, у которых самое большое количество `Book` и связанных с ним `Teacher`. Отсортировать по количеству в порядке убывания.
9. Выбрать `Teacher`, у которых самое большое количество `Book` у всех его `Student`. Отсортировать по количеству в порядке убывания.
10. Выбрать `Teacher` у которых количество `Book` у всех его `Student` находится между 7-ю и 11-и. Отсортировать по количеству в порядке убывания.
11. Вывести всех `last_name` и `name` всех `Teacher` и `Student` с полем `type` (student или teacher). Отсортировать в алфавитном порядке по `last_name`.
12. Добавить к существующей таблице `Student` колонку `rate`, в которой будет храниться курс, на котором студент сейчас находится (числовое значение от 1 до 6).
13. Этот пункт не обязателен к выполнению, но будет плюсом. Написать функцию, которая пройдется по всем `Book`, и выведет через запятую все `title`.

---

Вся инициализация структуры описана в файле `002_Students-Teachers-Books.sql` в каталоге mysql-init. Ниже только выборки из этой БД согласно условиям задач.

```sql
-- 5
SELECT *
FROM Student s
WHERE s.Last_name LIKE '%oro%';

-- 6
SELECT s.Last_name
    , count(s.Id) cnt
FROM Student s
GROUP BY s.Last_name
ORDER BY count(s.Id) DESC;

-- 7
SELECT s.Name
    , count(s.Id) cnt
FROM Student s
GROUP BY s.Name
ORDER BY count(s.Id) DESC
LIMIT 3;

-- 8
SELECT s.Last_name
    , s.Name
    , count(b.Id) cnt
    , t.Last_name
    , t.Name
FROM Student s
JOIN Book b on b.Student_id = s.Id
JOIN StudentTeacherLink stl on stl.Student_id = s.Id
JOIN Teacher t on t.Id = stl.Teacher_id
GROUP BY s.Last_name
    , s.Name
    , t.Last_name
    , t.Name
ORDER BY count(b.Id) DESC;
```

Похоже я неправильно сделал структуру таблиц, дальше разбираться и делать задания по SQL лень, т.к. основная цель это обучение Java, SQL и на работе хватает :)

## Домашнее задание 5

Ссылка на [статью](https://javarush.ru/groups/posts/3119-java-proekt-ot-a-do-ja-vse-chto-vih-khoteli-znatjh-o-maven).

ДЗ как такового в этот раз не было поэтому просто проделал то, что описано в статье. Создал проект в IDEA и описал помник со всеми описанными в статье опциями.

## Домашнее задание 6

Ссылка на [статью](https://javarush.ru/groups/posts/3120-java-proekt-ot-a-do-ja-vse-chto-vih-khoteli-znatjh-o-maven-chastjh-2).

Отдельного ДЗ снова не было. Проделал шаги из статьи. Нужно прочитать комментарии или хотя бы [статью](https://howtodoinjava.com/junit5/junit5-maven-dependency/) из одного комментария. Без `junit-jupiter-engine` тесты не запускаются, нужно подключить как зависимость в `pom.xml`.

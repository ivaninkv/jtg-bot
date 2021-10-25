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

Также написан init скрипт `001 Create two tables.sql` для `docker-compose`, приводящий БД в состояние по завершению этого ДЗ.

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

## Домашнее задание 7

Ссылка на [статью](https://javarush.ru/groups/posts/3157-java-proekt-ot-a-do-ja-springboot--flyway).

Отдельного ДЗ нет. В рамках этой статьи мы научились генерировать проект в [https://start.spring.io/](https://start.spring.io/). Также мы научились работать с миграциями в `flyway`. Есть нюанс, что в именах миграций должно быть два андерскора `__`, иначе `flyway` не воспринимает файлы как миграции. Также пришлось понизить версию `postgresql` до `13.4`, т.к. это последняя поддерживаемая `flyway` версия.

Полезные ссылки:
* [Евгений Борисов — Spring-построитель](https://www.youtube.com/watch?v=rd6wxPzXQvo&t=4178s)
* [Документация в спринге по Flyway](https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto-execute-flyway-database-migrations-on-startup)
* [Статья того же автора о шаблонных репозиториях на гитхабе](https://javarush.ru/groups/posts/2478-optimiziruem-rabotu-so-svoimi-proektami-na-github-znakomstvo-s-github-template-repository)

## Домашнее задание 8

Ссылка на [статью](https://javarush.ru/groups/posts/3170-java-proekt-ot-a-do-ja-planirovanie-proekta-semjh-raz-otmerjh--odin-raz-otrezhjh).

В статье говорилось о проектировании приложения и подготовке репозитория к работе. Очень полезная ссылка на гитхабе - [Insight -> Community](https://github.com/ivaninkv/jtg-bot/community). Там нужно пройтись по шагам и настроить свой репозиторий для коллективной работы.

Также полезно создать и настроить проект, чтобы вести разработку по `issue`. 

## Домашнее задание 9

Ссылка на [статью](https://javarush.ru/groups/posts/3179-java-proekt-ot-a-do-ja-pishem-proekt-dobavljaem-springboot-i-nastraivaem-ci-process).

В первой части статьи описывается как подключить спирногвый проект к уже существующему репозиторию, а также о настройке CI процесса в `Github Actions`. Спринг мы подключили ранее, поэтому в рамках этой статьи настраиваем `Github Actions`. Дополнительно пришлось добавить флаг `-DskipTests`, чтобы отключить тесты, т.к. пока тесты запускают приложение, а оно требует БД для применения миграций. Возможно в будущем будет понятно как обойти эту ситуацию.

## Домашнее задание 10

Ссылка на [статью](https://javarush.ru/groups/posts/3204-java-proekt-ot-a-do-ja-dobavljaem-telegram-bota-na-proekt).

В рамках этой статьи сделали следующее:
* Зарегистрировали Telegram бота `@javatg_bot`
* Подключили в проект [стартер](https://github.com/rubenlagus/TelegramBots/tree/master/telegrambots-spring-boot-starter)
* Добавили новый пакет в проект - `bot`
* Вынес настройки в отдельный файл `application-local.properties`, который добавлен в `.gitignore`

## Домашнее задание 11

Ссылка на [статью](https://javarush.ru/groups/posts/3219-java-proekt-ot-a-do-ja-realizuem-command-pattern-dlja-rabotih-s-botom-chastjh-1).

В рамках этой статьи сделали следующее:
* Добавили в проект 2 пакета `command` и `service`
* В `service` реализовали отправку сообщений ботом
* В `command` реализовали отдельные классы для каждой команды
* В `command` реализовали контейнер с командами, чтобы можно было использовать команды единообразно
* Поменяли код основного класса `JavaTelegramBot` для работы с новой структурой

## Домашнее задание 12

Ссылка на [статью](https://javarush.ru/groups/posts/3220-java-proekt-ot-a-do-ja-realizuem-command-pattern-dlja-rabotih-s-botom-chastjh-2).

В рамках этой статьи написали тесты для сервиса и команд.

## Домашнее задание 13

Ссылка на [статью](https://javarush.ru/groups/posts/3241-java-proekt-ot-a-do-ja-realizuem-razvertihvanie-prilozhenija).

В рамках этой статьи мы сделали следующее:
* Написали `Dockerfile`. Я немного дополнил файл из статьи - вынес всю конфигурацию в переменные окружения
* Все переменные окружения вынесены в файл `.env`, который в свою очередь, добавлен в `.gitignore`
* Поправили `compose.yml` для билда и запуска нашего приложения
* Написали скрипт `start.ps1` для запуска нашего приложения одной командой в консоли, если в дальнейшем планируется использовать этот скрипт для `CI/CD`, нужно будет написать новый на баше.

Полезные ссылки:
* [Работа с `.env`](https://docs.docker.com/engine/reference/commandline/container_inspect/)
* [`docker container inspect`](https://docs.docker.com/engine/reference/commandline/container_inspect/) - полезно при траблшутинге

## Домашнее задание 14

Ссылка на [статью](https://javarush.ru/groups/posts/3262-java-proekt-ot-a-do-ja-dobavljaem-vse-chto-svjazano-s-bd-chastjh-1).

В основном, все что описано в первой части было уже сделано в рамках других заданий. Из полезного, мы узнали про [spring profiles](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config.files.profile-specific). Раньше они были сконфигурированы через основной файл `application.properties`, а теперь заданы как опции VM в IDEA.

## Домашнее задание 15

Ссылка на [статью](https://javarush.ru/groups/posts/3264-java-proekt-ot-a-do-ja-dobavljaem-vse-chto-svjazano-s-bd-chastjh-2).

В рамках этой статьи мы сделали следующее:
* Создали слой `repository` - пакет `repository` и в нем пакет `entity`. Этот слой нужен для работы с БД через ORM
* Создали новый сервис(интерфейс) `TelegramUserService` для реализации `dependency inversion`, чтобы сервис одной сущности не общался напрямую с репозиторием другой сущности. Общение должно проходить только через сервис второй сущности, а уже сервис общается с репозиторием.
* Поправил в коде все использования `chatId` на новый тип данных - `long`
* Теперь при подключении пользователя информация о нем записывается в БД
* Поправили старые сломанные тесты
* Написали новые тесты
* Поработал с профилями `maven` и `spring-boot`. Это разные вещи - первый используется на этапе сборки, второй на этапе выполнения программы или при запуске тестов. Добавил несколько полезных ссылок на эту тему
* Поправил GitHub Action, добавив туда поднятие БД `postgresql`, важно использовать в качестве `url` для подключения `localhost`, в случае, если сама сборка запускается на раннере, а `postgresql` поднимается в контейнере. Вероятно, если делать `container job`, то нужно указывать имя сервиса для обращения из других контейнеров. Подробнее в документации. 

Полезные ссылки:
* [Статья про Spring Data JPA](https://habr.com/ru/post/435114/)
* [Документация Telegram Bot API](https://core.telegram.org/bots/api)
* [Проброс профиля мавен в спринг](http://dolszewski.com/spring/spring-boot-properties-per-maven-profile/)
* [Профили мавен](https://www.baeldung.com/maven-profiles)
* [Документация по профилям мавен](https://maven.apache.org/guides/introduction/introduction-to-profiles.html)
* [Документация по добавлению postgres в GitHub Action](https://docs.github.com/en/actions/using-containerized-services/creating-postgresql-service-containers)
* [Альтернативный вариант, ближе к тому, что описано в статье на javarush](https://github.com/marketplace/actions/setup-postgresql)

## Настраиваем автоматический деплой

Это активность без статьи автора - сделаем автоматический деплой на сервер, развернутый в [Oracle Cloud Free Tier](https://www.oracle.com/ru/cloud/free/)

* Разворачиваем сервер на Oracle Linux 8 для доступа к более свежему ПО
* Ставим на сервер `git`, `docker`, `docker compose`
* Включаем [swarm mode](https://docs.docker.com/engine/swarm/swarm-mode/), т.к. подсеть `10.0.0.0/8` уже занята, зададим другую при включении этого режима. Команда будет следующей:
```shell
docker swarm init --default-addr-pool 10.10.0.0/8
```
* Настраиваем в репозитории `Environment` и секреты для него
* Правим GitHub Action workflow по [инструкции](https://docs.github.com/en/packages/managing-github-packages-using-github-actions-workflows)
* Генерим на сервере новый SSH ключ и прописываем его в `~/.ssh/authorized_keys`
* Добавляем новые секреты в GitHub (SSH_USER, SSH_PUBLIC_KEY и SSH_PRIVATE_KEY)
* Пишем новый `compose-deploy.yml` файл для деплоя в Docker Swarm
* Правим `Workflow` для деплоя приложения, используя следующие Actions:
  * [scp-action](https://github.com/appleboy/scp-action) для копирования файлов на удаленный сервер 
  * [ssh-action](https://github.com/appleboy/ssh-action) для выполнения команд на удаленном сервере

## Тестовый бот

И еще одна активность без статьи, хотя отсылки к этому были еще в самом начале цикла статей. Регистрируем тестового бота и настраиваем его деплой на тот же сервер, но как другой `stack` докера. В GitHub Action создаем отдельный Environment TEST и закидываем туда все нужные секреты. 

## Домашнее задание 16

Ссылка на [статью](https://javarush.ru/groups/posts/3300-java-proekt-ot-a-do-ja-dobavljaem-vozmozhnostjh-podpisatjhsja-na-gruppu-statey-chastjh-1).

В рамках этой статьи мы сделали следующее:
* Для работы с API подключили библиотеку `unirest-java`
* Добавили `dto`-шек для работы с API
* Вынесли адрес API в настройки
* Написали тесты

Полезные ссылки:
* [Статья про сваггер](https://habr.com/ru/post/434798/)

## Домашнее задание 17

Ссылка на [статью](https://javarush.ru/groups/posts/3313-java-proekt-ot-a-do-ja-dobavljaem-vozmozhnostjh-podpisatjhsja-na-gruppu-statey-chastjh-2).

В рамках этой статьи мы сделали следующее:
* Добавили новую миграцию БД
* Добавили новый `entity` и отредактировали старый
* Добавили новый репозиторий и написали к нему интеграционные тесты
* Написали слой сервисов для работы с подписками на группы
* Добавили новую команду `addgroupsub`

## Домашнее задание 18

Ссылка на [статью](https://javarush.ru/groups/posts/3314-java-proekt-ot-a-do-ja-dobavljaem-vozmozhnostjh-podpisatjhsja-na-gruppu-statey-chastjh-3).

В рамках этой статьи мы сделали:
* Добавили команду `listGroupSub`
* Написали для нее тест
* Причесали текст в командах

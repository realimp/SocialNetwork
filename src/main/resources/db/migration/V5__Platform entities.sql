CREATE TABLE IF NOT EXISTS language (
id INTEGER AUTO_INCREMENT,
title VARCHAR(30) NOT NULL,
PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS country (
id INTEGER AUTO_INCREMENT,
title VARCHAR(30) NOT NULL,
PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS city (
id INTEGER AUTO_INCREMENT,
country_id INTEGER NOT NULL,
title VARCHAR(30) NOT NULL,
PRIMARY KEY (id)
);

ALTER TABLE city ADD FOREIGN KEY (country_id) REFERENCES country(id);

INSERT INTO language (title) values ('Русский');
INSERT INTO language (title) values ('English');

INSERT INTO country (title) values ('Россия');
INSERT INTO country (title) values ('Соединенные Штаты Америки');
INSERT INTO country (title) values ('Германия');
INSERT INTO country (title) values ('Китай');
INSERT INTO country (title) values ('Австралия');

INSERT INTO city (country_id, title) values (1,'Москва');
INSERT INTO city (country_id, title) values (1,'Санкт-Петербург');
INSERT INTO city (country_id, title) values (1,'Екатеринбург');
INSERT INTO city (country_id, title) values (2,'Вашингтон');
INSERT INTO city (country_id, title) values (2,'Нью-Йорк');
INSERT INTO city (country_id, title) values (2,'Лос-Анджелес');
INSERT INTO city (country_id, title) values (3,'Берлин');
INSERT INTO city (country_id, title) values (3,'Гамбург');
INSERT INTO city (country_id, title) values (3,'Мюнхен');
INSERT INTO city (country_id, title) values (4,'Пекин');
INSERT INTO city (country_id, title) values (4,'Шанхай');
INSERT INTO city (country_id, title) values (4,'Тяньцзинь');
INSERT INTO city (country_id, title) values (5,'Сидней');
INSERT INTO city (country_id, title) values (5,'Мельбурн');
INSERT INTO city (country_id, title) values (5,'Брисбен');
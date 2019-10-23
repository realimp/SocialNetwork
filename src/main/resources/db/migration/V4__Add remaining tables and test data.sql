-- CREATING TABLES

CREATE TABLE IF NOT EXISTS notification_settings (
id INTEGER AUTO_INCREMENT,
person_id INTEGER NOT NULL,
notification_type_id INTEGER NOT NULL,
enable INTEGER,
PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS notification_type (
id INTEGER,
code INTEGER NOT NULL,
name VARCHAR(30),
PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS friendship (
id INTEGER,
src_person_id INTEGER NOT NULL,
dst_person_id INTEGER NOT NULL,
code VARCHAR(50),
PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS person2dialog (
id INTEGER AUTO_INCREMENT,
person_id INTEGER NOT NULL,
dialog_id INTEGER NOT NULL,
PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS dialog (
id INTEGER,
owner_id INTEGER NOT NULL,
unread_count INTEGER,
is_deleted BIT NOT NULL DEFAULT 0,
invite_code VARCHAR(20),
PRIMARY KEY (id, invite_code)
);

CREATE TABLE IF NOT EXISTS tag (
id INTEGER,
tag VARCHAR(50),
PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS notification (
id INTEGER AUTO_INCREMENT	,
type_id INTEGER NOT NULL,
sent_time DATETIME NOT NULL,
person_id INTEGER NOT NULL,
entity_id INTEGER NOT NULL,
contact VARCHAR(50),
is_readed BIT NOT NULL DEFAULT 0,
PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user (
id INTEGER AUTO_INCREMENT,
name VARCHAR(30) NOT NULL,
e_mail VARCHAR(50) NOT NULL,
password VARCHAR(50) NOT NULL,
type VARCHAR(50) NOT NULL,
PRIMARY KEY (id, e_mail)
);

CREATE TABLE IF NOT EXISTS block_history (
id INTEGER AUTO_INCREMENT,
time DATETIME,
person_id INTEGER NOT NULL,
post_id INTEGER NOT NULL,
comment_id INTEGER NOT NULL,
action VARCHAR(20),
PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS post_comment (
id INTEGER,
time DATETIME,
post_id INTEGER NOT NULL,
parent_id INTEGER NOT NULL,
author_id INTEGER NOT NULL,
comment_text VARCHAR(50),
is_blocked BIT NOT NULL DEFAULT 0,
is_deleted BIT NOT NULL DEFAULT 0,
PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS post2tag (
id INTEGER AUTO_INCREMENT,
post_id INTEGER NOT NULL,
tag_id INTEGER NOT NULL,
PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS comment_like (
id INTEGER AUTO_INCREMENT,
time DATETIME,
person_id INTEGER NOT NULL,
comment_id INTEGER NOT NULL,
PRIMARY KEY (id)
);


-- CREATING FOREIGN KEYS
ALTER TABLE message ADD FOREIGN KEY (dialog_id) REFERENCES dialog(id);

ALTER TABLE notification_settings ADD FOREIGN KEY (notification_type_id) REFERENCES notification_type(id);

ALTER TABLE notification ADD FOREIGN KEY (type_id) REFERENCES notification_type(id);
ALTER TABLE notification ADD FOREIGN KEY (entity_id) REFERENCES friendship(id);
ALTER TABLE notification ADD FOREIGN KEY (entity_id) REFERENCES message(id);
ALTER TABLE notification ADD FOREIGN KEY (person_id) REFERENCES person(id);
ALTER TABLE notification ADD FOREIGN KEY (entity_id) REFERENCES post(id);
ALTER TABLE notification ADD FOREIGN KEY (entity_id) REFERENCES post_comment(id);

ALTER TABLE friendship ADD FOREIGN KEY (src_person_id) REFERENCES person(id);
ALTER TABLE friendship ADD FOREIGN KEY (dst_person_id) REFERENCES person(id);

ALTER TABLE block_history ADD FOREIGN KEY (person_id) REFERENCES person(id);
ALTER TABLE block_history ADD FOREIGN KEY (post_id) REFERENCES post(id);
ALTER TABLE block_history ADD FOREIGN KEY (comment_id) REFERENCES post_comment(id);

ALTER TABLE post_comment ADD FOREIGN KEY (post_id) REFERENCES post(id);
ALTER TABLE post_comment ADD FOREIGN KEY (author_id) REFERENCES person(id);
ALTER TABLE post_comment ADD FOREIGN KEY (parent_id) REFERENCES post_comment(id);

ALTER TABLE comment_like ADD FOREIGN KEY (person_id) REFERENCES person(id);
ALTER TABLE comment_like ADD FOREIGN KEY (comment_id) REFERENCES post_comment(id);

ALTER TABLE post2tag ADD FOREIGN KEY (post_id) REFERENCES post_comment(id);
ALTER TABLE post2tag ADD FOREIGN KEY (tag_id) REFERENCES tag(id);

ALTER TABLE person2dialog ADD FOREIGN KEY (person_id) REFERENCES person(id);
ALTER TABLE person2dialog ADD FOREIGN KEY (dialog_id) REFERENCES dialog(id);

-- Filling tables with test data:

-- Person
DELETE FROM person;
INSERT INTO person values (1, "Pavel", "Dobromirov", "2010-01-01 00:00:00", "1992-12-23 00:00:00", "paveldobro92@mail.ru", "+79031119090", "NobodyKnows3", null, null, "Moscow", "Russia", null, 1, "ALL", null, 0, 0, 0);
INSERT INTO person values (2, "Oleg", "Popov", "2011-01-01 00:00:00", "1991-01-15 00:00:00", "olegpopov232@mail.ru", "+79031119190", "NobodyTells3", null, null, "Ekaterinburg", "Russia", null, 1, "ALL", null, 0, 0, 0);
INSERT INTO person values (3, "Petr", "Nevol'nyj", "2012-01-01 00:00:00", "1990-02-23 00:00:00", "petrsvoboda90@mail.ru", "+79031119290", "NobodyWhispers3", null, null, "St. Petersburg", "Russia", null, 1, "FRIENDS", null, 0, 0, 0);
INSERT INTO person values (4, "Kseniya", "Mannik", "2013-01-01 00:00:00", "1989-05-22 00:00:00", "ksusha.m.353@mail.ru", "+79031119093", "NobodyLies3", null, null, "Sochi", "Russia", null, 1, "ALL", null, 0, 0, 0);
INSERT INTO person values (5, "Vera", "Levina", "2014-01-01 00:00:00", "1988-08-08 00:00:00", "vlevina88@mail.ru", "+79031119095", "NobodyTries3", null, null, "Erevan", "Armeniya", null, 1, "FRIENDS", null, 0, 0, 0);

-- Friendship
DELETE FROM friendship;
INSERT INTO friendship values (1, 1, 2, "REQUEST");
INSERT INTO friendship values (2, 1, 3, "FRIEND");
INSERT INTO friendship values (3, 1, 4, "BLOCKED");
INSERT INTO friendship values (4, 1, 5, "DECLINED");
INSERT INTO friendship values (5, 2, 5, "SUBSCRIBED");

-- Dialog
DELETE FROM dialog;
INSERT INTO dialog values (1, 1, null, 0, "INVITE_CODE");
INSERT INTO dialog values (2, 1, null, 0, "INVITE_CODE");
INSERT INTO dialog values (3, 3, null, 0, "INVITE_CODE");
INSERT INTO dialog values (4, 4, null, 0, "INVITE_CODE");
INSERT INTO dialog values (5, 5, null, 0, "INVITE_CODE");

-- Person2dialog
DELETE FROM person2dialog;
INSERT INTO person2dialog values (1, 1, 1);
INSERT INTO person2dialog values (2, 1, 2);
INSERT INTO person2dialog values (3, 3, 3);
INSERT INTO person2dialog values (4, 4, 4);
INSERT INTO person2dialog values (5, 5, 5);

-- Message
DELETE FROM message;
INSERT INTO message values (1, "2019-10-23 10:00:00", 1, 2, "Принимай заявку в друзья!", "SENT", 1, 0);
INSERT INTO message values (2, "2019-10-23 11:00:00", 1, 3, "Петя, здорово! Как у тебя дела?", "READ", 2, 0);
INSERT INTO message values (3, "2019-10-23 11:02:00", 3, 1, "Паша, привет :) Ходили в субботу в театр", "READ", 3, 0);
INSERT INTO message values (4, "2019-10-23 12:15:00", 4, 5, "Меня Паша за спам заблокировал :(", "READ", 4, 0);
INSERT INTO message values (5, "2019-10-23 13:05:00", 5, 3, "Петька, зачем убежал со встречи вчера?", "READ", 5, 0);

-- Notification_type
DELETE FROM notification_type;
INSERT INTO notification_type values (1, 10, "POST");
INSERT INTO notification_type values (2, 20, "POST_COMMENT");
INSERT INTO notification_type values (3, 30, "COMMENT_COMMENT");
INSERT INTO notification_type values (4, 40, "FRIEND_REQUEST");
INSERT INTO notification_type values (5, 50, "MESSAGE");

-- Notification_settings
DELETE FROM notification_settings;
INSERT INTO notification_settings values (1, 1, 1, 1);
INSERT INTO notification_settings values (2, 2, 2, 0);
INSERT INTO notification_settings values (3, 3, 1, 1);
INSERT INTO notification_settings values (4, 4, 3, 1);
INSERT INTO notification_settings values (5, 5, 5, 1);

-- Post
DELETE FROM post;
INSERT INTO post values (1, "2019-10-22 12:00:00", 1, "First post ever!", "Welcome everybody! Our social network has opened!", 0 , 0);
INSERT INTO post values (2, "2019-10-22 13:00:00", 2, "Непутевые заметки", "Здесь я хотел бы рассказать о последнем путешествии...", 0 , 0);
INSERT INTO post values (3, "2019-10-22 14:00:00", 3, "Мир музыки", "Всем привет, сегодня мы поговорим об особенностях звучания различных электрогитар...", 0 , 0);
INSERT INTO post values (4, "2019-10-22 15:00:00", 4, "Nail studio", "Эти ребята дают скидку 20% на сеанс маникюра за репост! Они просто космос!", 0 , 0);
INSERT INTO post values (5, "2019-10-22 16:00:00", 5, "Careerist", "Стоит ли просить прибавку к зарплате у начальника или она вырастет сама собой?...", 0 , 0);

-- Post_like
DELETE FROM post_like;
INSERT INTO post_like values (1, "2019-10-22 12:01:00", 2, 1);
INSERT INTO post_like values (2, "2019-10-22 13:01:00", 5, 2);
INSERT INTO post_like values (3, "2019-10-22 14:01:00", 2, 3);
INSERT INTO post_like values (4, "2019-10-22 15:01:00", 1, 4);
INSERT INTO post_like values (5, "2019-10-22 16:01:00", 4, 5);

-- Post_file
DELETE FROM post_file;
INSERT INTO post_file values (1, 1, "File1.txt", "files/notes/");
INSERT INTO post_file values (2, 2, "Img1.jpg", "files/images/");
INSERT INTO post_file values (3, 3, "Img2.jpg", "files/images/");
INSERT INTO post_file values (4, 4, "Img3.jpg", "files/images/");
INSERT INTO post_file values (5, 5, "Img4.jpg", "files/images/");

-- Post_comment
DELETE FROM post_comment;
INSERT INTO post_comment values (1, "2019-10-22 12:02:00", 1, 1, 1, "Comment 1", 0, 0);
INSERT INTO post_comment values (2, "2019-10-22 13:02:00", 2, 2, 2, "Comment 2", 0, 0);
INSERT INTO post_comment values (3, "2019-10-22 14:02:00", 3, 3, 3, "Comment 3", 0, 0);
INSERT INTO post_comment values (4, "2019-10-22 15:02:00", 4, 4, 4, "Comment 4", 0, 0);
INSERT INTO post_comment values (5, "2019-10-22 16:02:00", 5, 5, 5, "Comment 5", 0, 0);

-- Notification
DELETE FROM notification;
INSERT INTO notification values (1, 4, "2019-10-23 12:00:00", 1, 1, "paveldobro92@mail.ru", 1);
INSERT INTO notification values (2, 1, "2019-10-23 12:00:00", 2, 1, "olegpopov232@mail.ru", 1);
INSERT INTO notification values (3, 2, "2019-10-23 12:00:00", 3, 1, "petrsvoboda90@mail.ru", 1);
INSERT INTO notification values (4, 3, "2019-10-23 12:00:00", 4, 1, "ksusha.m.353@mail.ru", 1);
INSERT INTO notification values (5, 5, "2019-10-23 12:00:00", 5, 1, "vlevina88@mail.ru", 1);

-- User
DELETE FROM user;
INSERT INTO user values (1, "pavel", "paveldobro92@mail.ru", "test_pass322","ADMIN");
INSERT INTO user values (2, "oleg", "olegpopov232@mail.ru", "test_pass323","MODERATOR");
INSERT INTO user values (3, "petr", "petrsvoboda90@mail.ru", "test_pass324","MODERATOR");
INSERT INTO user values (4, "ksusha", "ksusha.m.353@mail.ru", "test_pass325","MODERATOR");
INSERT INTO user values (5, "vera", "vlevina88@mail.ru", "test_pass326","MODERATOR");

-- Block_history
DELETE FROM block_history;
INSERT INTO block_history values (1, "2019-10-23 12:00:00", 1, 1, 1, "BLOCK");
INSERT INTO block_history values (2, "2019-10-23 13:00:00", 2, 2, 2, "BLOCK");
INSERT INTO block_history values (3, "2019-10-23 14:00:00", 3, 3, 3, "BLOCK");
INSERT INTO block_history values (4, "2019-10-23 15:00:00", 4, 4, 4, "BLOCK");
INSERT INTO block_history values (5, "2019-10-23 16:00:00", 5, 5, 5, "BLOCK");

-- Comment_like
DELETE FROM comment_like;
INSERT INTO comment_like values (1, "2019-10-23 12:00:00", 2, 1);
INSERT INTO comment_like values (2, "2019-10-23 13:00:00", 5, 2);
INSERT INTO comment_like values (3, "2019-10-23 14:00:00", 4, 3);
INSERT INTO comment_like values (4, "2019-10-23 15:00:00", 1, 4);
INSERT INTO comment_like values (5, "2019-10-23 16:00:00", 3, 5);

-- Tag
DELETE FROM tag;
INSERT INTO tag values (10, "#TAG1");
INSERT INTO tag values (20, "#TAG2");
INSERT INTO tag values (30, "#TAG3");
INSERT INTO tag values (40, "#TAG4");
INSERT INTO tag values (50, "#TAG5");

-- Post2tag
DELETE FROM post2tag;
INSERT INTO post2tag values (1, 1, 10);
INSERT INTO post2tag values (2, 2, 20);
INSERT INTO post2tag values (3, 2, 30);
INSERT INTO post2tag values (4, 3, 40);
INSERT INTO post2tag values (5, 4, 50);
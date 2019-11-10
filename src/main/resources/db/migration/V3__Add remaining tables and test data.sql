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
id INTEGER AUTO_INCREMENT,
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
INSERT INTO person (id, first_name, last_name, reg_date, birth_date, e_mail, phone, password,
                    city, country, is_approved, messages_permission, is_blocked, is_online, is_deleted)
                    values (1, 'Pavel', 'Dobromirov', '2010-01-01 00:00:00', '1992-12-23 00:00:00', 'paveldobro92@mail.ru',
                    '+79031119090', '$2y$12$h8ox82c5T9q.nbJR02vKKevSpOunDwcyAB5BvACraV8TD8hG438ae', 'Moscow', 'Russia', 1, 'ALL', 0, 0, 0);
INSERT INTO person (id, first_name, last_name, reg_date, birth_date, e_mail, phone, password,
                   city, country, is_approved, messages_permission, is_blocked, is_online, is_deleted)
                   values (2, 'Oleg', 'Popov', '2011-01-01 00:00:00', '1991-01-15 00:00:00', 'olegpopov232@mail.ru',
                   '+79031119190', '$2y$12$h8ox82c5T9q.nbJR02vKKevSpOunDwcyAB5BvACraV8TD8hG438ae', 'Ekaterinburg', 'Russia', 1, 'ALL', 0, 0, 0);
INSERT INTO person (id, first_name, last_name, reg_date, birth_date, e_mail, phone, password,
                    city, country, is_approved, messages_permission, is_blocked, is_online, is_deleted)
                    values (3, 'Petr', 'Nevolnyj', '2012-01-01 00:00:00', '1990-02-23 00:00:00', 'petrsvoboda90@mail.ru',
                    '+79031119290', '$2y$12$h8ox82c5T9q.nbJR02vKKevSpOunDwcyAB5BvACraV8TD8hG438ae', 'St. Petersburg', 'Russia', 1, 'FRIENDS', 0, 0, 0);

-- Friendship
DELETE FROM friendship;
INSERT INTO friendship (id, src_person_id, dst_person_id, code) values (1, 1, 2, 'REQUEST');
INSERT INTO friendship (id, src_person_id, dst_person_id, code) values (2, 1, 3, 'FRIEND');
INSERT INTO friendship (id, src_person_id, dst_person_id, code) values (3, 1, 2, 'BLOCKED');

-- Dialog
DELETE FROM dialog;
INSERT INTO dialog (id, owner_id, unread_count, is_deleted, invite_code) values (1, 1, null, 0, 'INVITE_CODE');
INSERT INTO dialog (id, owner_id, unread_count, is_deleted, invite_code) values (2, 1, null, 0, 'INVITE_CODE');
INSERT INTO dialog (id, owner_id, unread_count, is_deleted, invite_code) values (3, 3, null, 0, 'INVITE_CODE');

-- Person2dialog
DELETE FROM person2dialog;
INSERT INTO person2dialog (id, person_id, dialog_id) values (1, 1, 1);
INSERT INTO person2dialog (id, person_id, dialog_id) values (2, 1, 2);
INSERT INTO person2dialog (id, person_id, dialog_id) values (3, 3, 3);

-- Message
DELETE FROM message;
INSERT INTO message (id, time, author_id, recipient_id, message_text, read_status, dialog_id, is_deleted)
                    values (1, '2019-10-23 10:00:00', 1, 2, 'Принимай заявку в друзья!', 'SENT', 1, 0);
INSERT INTO message (id, time, author_id, recipient_id, message_text, read_status, dialog_id, is_deleted)
                    values (2, '2019-10-23 11:00:00', 1, 3, 'Петя, здорово! Как у тебя дела?', 'READ', 2, 0);
INSERT INTO message (id, time, author_id, recipient_id, message_text, read_status, dialog_id, is_deleted)
                    values (3, '2019-10-23 11:02:00', 3, 1, 'Паша, привет :) Ходили в субботу в театр', 'READ', 3, 0);

-- Notification_type
DELETE FROM notification_type;
INSERT INTO notification_type (id, code, name) values (1, 10, 'POST');
INSERT INTO notification_type (id, code, name) values (2, 20, 'POST_COMMENT');
INSERT INTO notification_type (id, code, name) values (3, 30, 'COMMENT_COMMENT');

-- Notification_settings
DELETE FROM notification_settings;
INSERT INTO notification_settings (id, person_id, notification_type_id, enable) values (1, 1, 1, 1);
INSERT INTO notification_settings (id, person_id, notification_type_id, enable) values (2, 2, 2, 0);
INSERT INTO notification_settings (id, person_id, notification_type_id, enable) values (3, 3, 1, 1);

-- Post
DELETE FROM post;
INSERT INTO post (id, time, author_id, title, post_text, is_blocked, is_deleted)
                 values (1, '2019-10-22 12:00:00', 1, 'First post ever!',
                 'Welcome everybody! Our social network has opened!', 0 , 0);
INSERT INTO post (id, time, author_id, title, post_text, is_blocked, is_deleted)
                 values (2, '2019-10-22 13:00:00', 2, 'Непутевые заметки',
                 'Здесь я хотел бы рассказать о последнем путешествии...', 0 , 0);
INSERT INTO post (id, time, author_id, title, post_text, is_blocked, is_deleted)
                 values (3, '2019-10-22 14:00:00', 3, 'Мир музыки',
                 'Всем привет, сегодня мы поговорим об особенностях звучания различных электрогитар...', 0 , 0);

-- Post_like
DELETE FROM post_like;
INSERT INTO post_like (id, time, person_id, post_id) values (1, '2019-10-22 12:01:00', 2, 1);
INSERT INTO post_like (id, time, person_id, post_id) values (2, '2019-10-22 13:01:00', 3, 2);
INSERT INTO post_like (id, time, person_id, post_id) values (3, '2019-10-22 14:01:00', 2, 3);

-- Post_file
DELETE FROM post_file;
INSERT INTO post_file (id, post_id, name, path) values (1, 1, 'File1.txt', 'files/notes/');
INSERT INTO post_file (id, post_id, name, path) values (2, 2, 'Img1.jpg', 'files/images/');
INSERT INTO post_file (id, post_id, name, path) values (3, 3, 'Img2.jpg', 'files/images/');

-- Post_comment
DELETE FROM post_comment;
INSERT INTO post_comment (id, time, post_id, parent_id, author_id, comment_text, is_blocked, is_deleted)
                         values (1, '2019-10-22 12:02:00', 1, 1, 1, 'Comment 1', 0, 0);
INSERT INTO post_comment (id, time, post_id, parent_id, author_id, comment_text, is_blocked, is_deleted)
                         values (2, '2019-10-22 13:02:00', 2, 2, 2, 'Comment 2', 0, 0);
INSERT INTO post_comment (id, time, post_id, parent_id, author_id, comment_text, is_blocked, is_deleted)
                         values (3, '2019-10-22 14:02:00', 3, 3, 3, 'Comment 3', 0, 0);

-- Notification
DELETE FROM notification;
INSERT INTO notification (id, type_id, sent_time, person_id, entity_id, contact, is_readed)
                         values (1, 2, '2019-10-23 12:00:00', 1, 1, 'paveldobro92@mail.ru', 1);
INSERT INTO notification (id, type_id, sent_time, person_id, entity_id, contact, is_readed)
                         values (2, 1, '2019-10-23 12:00:00', 2, 1, 'olegpopov232@mail.ru', 1);
INSERT INTO notification (id, type_id, sent_time, person_id, entity_id, contact, is_readed)
                         values (3, 2, '2019-10-23 12:00:00', 3, 1, 'petrsvoboda90@mail.ru', 1);

-- Block_history
DELETE FROM block_history;
INSERT INTO block_history (id, time, person_id, post_id, comment_id, action) values (1, '2019-10-23 12:00:00', 1, 1, 1, 'BLOCK');
INSERT INTO block_history (id, time, person_id, post_id, comment_id, action) values (2, '2019-10-23 13:00:00', 2, 2, 2, 'BLOCK');
INSERT INTO block_history (id, time, person_id, post_id, comment_id, action) values (3, '2019-10-23 14:00:00', 3, 3, 3, 'BLOCK');

-- Comment_like
DELETE FROM comment_like;
INSERT INTO comment_like (id, time, person_id, comment_id) values (1, '2019-10-23 12:00:00', 2, 1);
INSERT INTO comment_like (id, time, person_id, comment_id) values (2, '2019-10-23 13:00:00', 1, 2);
INSERT INTO comment_like (id, time, person_id, comment_id) values (3, '2019-10-23 14:00:00', 2, 3);

-- Tag
DELETE FROM tag;
INSERT INTO tag (id, tag) values (10, '#TAG1');
INSERT INTO tag (id, tag) values (20, '#TAG2');
INSERT INTO tag (id, tag) values (30, '#TAG3');

-- Post2tag
DELETE FROM post2tag;
INSERT INTO post2tag (id, post_id, tag_id) values (1, 1, 10);
INSERT INTO post2tag (id, post_id, tag_id) values (2, 2, 20);
INSERT INTO post2tag (id, post_id, tag_id) values (3, 2, 30);


-- Test data
insert into person(id, first_name, last_name, e_mail, phone, password)
values (4, 'testName', 'testLastName', 'test@email.com', '8800200600', '$2y$12$h8ox82c5T9q.nbJR02vKKevSpOunDwcyAB5BvACraV8TD8hG438ae');

UPDATE `social_network`.`person` SET `password` = '$2y$12$h8ox82c5T9q.nbJR02vKKevSpOunDwcyAB5BvACraV8TD8hG438ae';
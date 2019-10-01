/* FOR TEST:
 drop database social_network;
 create database social_network;
 use social_network;
*/

-- CREATING TABLES

CREATE TABLE IF NOT EXISTS person (
id INTEGER,
first_name VARCHAR(30) NOT NULL,
last_name VARCHAR(30) NOT NULL,
reg_date DATETIME,
birth_date DATETIME,
e_mail VARCHAR(50),
phone VARCHAR(50) NOT NULL,
password VARCHAR(50) NOT NULL,
photo VARCHAR(100),
about VARCHAR(500),
city VARCHAR(50),
country VARCHAR(50),
confirmation_code VARCHAR(20),
is_approved INTEGER,
messages_permission VARCHAR(50),
last_online_time DATETIME,
is_blocked BIT NOT NULL DEFAULT 0,
is_online BIT NOT NULL DEFAULT 0,
is_deleted BIT NOT NULL DEFAULT 0,
PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS message (
id INTEGER,
time DATETIME,
author_id INTEGER NOT NULL,
recipient_id INTEGER NOT NULL,
message_text VARCHAR(500),
read_status VARCHAR(30),
dialog_id INTEGER NOT NULL,
is_deleted BIT NOT NULL DEFAULT 0,
PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS post (
id INTEGER,
time DATETIME,
author_id INTEGER NOT NULL,
title VARCHAR(50),
post_text VARCHAR(500),
is_blocked BIT NOT NULL DEFAULT 0,
is_deleted BIT NOT NULL DEFAULT 0,
PRIMARY KEY (id)
);


/*
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

CREATE TABLE IF NOT EXISTS post_like (
id INTEGER AUTO_INCREMENT,
time DATETIME,
person_id INTEGER NOT NULL,
post_id INTEGER NOT NULL,
PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS post_file (
id INTEGER AUTO_INCREMENT,
post_id INTEGER NOT NULL,
name VARCHAR(50),
path VARCHAR(50),
PRIMARY KEY (id)
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
*/

-- CREATING FOREIGN KEYS
ALTER TABLE message ADD FOREIGN KEY (author_id) REFERENCES person(id);
ALTER TABLE message ADD FOREIGN KEY (recipient_id) REFERENCES person(id);
-- ALTER TABLE message ADD FOREIGN KEY (dialog_id) REFERENCES dialog(id);

ALTER TABLE post ADD FOREIGN KEY (author_id) REFERENCES person(id);

/*
ALTER TABLE notification_settings ADD FOREIGN KEY (notification_type_id) REFERENCES notification_type(id);

ALTER TABLE notification ADD FOREIGN KEY (type_id) REFERENCES notification_type(id);
ALTER TABLE notification ADD FOREIGN KEY (entity_id) REFERENCES friendship(id);
ALTER TABLE notification ADD FOREIGN KEY (entity_id) REFERENCES message(id);
ALTER TABLE notification ADD FOREIGN KEY (person_id) REFERENCES person(id);
ALTER TABLE notification ADD FOREIGN KEY (entity_id) REFERENCES post(id);
ALTER TABLE notification ADD FOREIGN KEY (entity_id) REFERENCES post_comment(id);

ALTER TABLE friendship ADD FOREIGN KEY (src_person_id) REFERENCES person(id);
ALTER TABLE friendship ADD FOREIGN KEY (dst_person_id) REFERENCES person(id);

ALTER TABLE post_like ADD FOREIGN KEY (person_id) REFERENCES person(id);
ALTER TABLE post_like ADD FOREIGN KEY (post_id) REFERENCES post(id);

ALTER TABLE post_file ADD FOREIGN KEY (post_id) REFERENCES post(id);

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
*/

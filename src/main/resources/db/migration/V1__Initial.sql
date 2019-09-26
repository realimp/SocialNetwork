CREATE TABLE IF NOT EXISTS notification_settings (
id INTEGER PRIMARY KEY,
person_id INTEGER NOT NULL,
notification_type_id INTEGER NOT NULL,
enable INTEGER
);

CREATE TABLE IF NOT EXISTS notification_type (
id INTEGER PRIMARY KEY,
code INTEGER,
name VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS friendship (
id INTEGER PRIMARY KEY,
src_person_id INTEGER NOT NULL,
dst_person_id INTEGER NOT NULL,
code VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS message (
id INTEGER PRIMARY KEY,
time DATETIME,
author_id INTEGER NOT NULL,
recipient_id INTEGER NOT NULL,
message_text VARCHAR(500),
read_status VARCHAR(30),
dialog_id INTEGER NOT NULL,
is_deleted INTEGER
);

CREATE TABLE IF NOT EXISTS person (
id INTEGER PRIMARY KEY,
first_name VARCHAR(30),
last_name VARCHAR(30),
reg_date DATETIME,
birth_date DATETIME,
e_mail VARCHAR(50),
phone VARCHAR(50) NOT NULL,
password VARCHAR(50),
photo VARCHAR(50),
about VARCHAR(50),
city VARCHAR(50),
country VARCHAR(50),
confirmation_code VARCHAR(20),
is_approved INTEGER,
messages_permission VARCHAR(50),
last_online_time DATETIME,
is_blocked INTEGER,
is_online INTEGER,
is_deleted INTEGER
);

CREATE TABLE IF NOT EXISTS post (
id INTEGER PRIMARY KEY,
time DATETIME,
author_id INTEGER NOT NULL,
title VARCHAR(50),
post_text VARCHAR(500),
is_blocked INTEGER,
is_deleted INTEGER
);

CREATE TABLE IF NOT EXISTS person2dialog (
id INTEGER PRIMARY KEY,
person_id INTEGER NOT NULL,
dialog_id INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS dialog (
id INTEGER PRIMARY KEY,
owner_id INTEGER,
unread_count INTEGER,
is_deleted INTEGER,
invite_code VARCHAR(20) -- KEY ??
);

CREATE TABLE IF NOT EXISTS tag (
id INTEGER PRIMARY KEY,
tag VARCHAR(50)
);


CREATE TABLE IF NOT EXISTS notification (
id INTEGER PRIMARY KEY,
type_id INTEGER NOT NULL,
sent_time DATETIME,
person_id INTEGER NOT NULL,
entity_id INTEGER NOT NULL,
contact VARCHAR(50),
is_readed INTEGER
);

CREATE TABLE IF NOT EXISTS user (
id INTEGER PRIMARY KEY,
name VARCHAR(30),
e_mail VARCHAR(50), -- KEY ??
password VARCHAR(50),
type VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS post_like (
id INTEGER PRIMARY KEY,
time DATETIME,
person_id INTEGER NOT NULL,
post_id INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS post_file (
id INTEGER PRIMARY KEY,
post_id INTEGER NOT NULL,
name VARCHAR(50),
path VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS block_history (
id INTEGER PRIMARY KEY,
time DATETIME,
person_id INTEGER NOT NULL,
post_id INTEGER NOT NULL,
comment_id INTEGER NOT NULL,
action VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS post_comment (
id INTEGER PRIMARY KEY,
time DATETIME,
post_id INTEGER NOT NULL,
parent_id INTEGER NOT NULL,
author_id INTEGER NOT NULL,
comment_text VARCHAR(50),
is_blocked INTEGER,
is_deleted INTEGER
);

CREATE TABLE IF NOT EXISTS post2tag (
id INTEGER PRIMARY KEY,
post_id INTEGER NOT NULL,
tag_id INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS comment_like (
id INTEGER PRIMARY KEY,
time DATETIME,
person_id INTEGER NOT NULL,
comment_id INTEGER NOT NULL
);

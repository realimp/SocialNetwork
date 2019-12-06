-- CREATING FOREIGN KEYS notification_settings
ALTER TABLE notification_settings ADD FOREIGN KEY (person_id) REFERENCES person(id);

-- Notification
ALTER TABLE social_network.notification ADD recipient_id INTEGER NOT NULL;
UPDATE social_network.notification SET recipient_id = 3 WHERE id = 1;
UPDATE social_network.notification SET recipient_id = 3 WHERE id = 2;
UPDATE social_network.notification SET recipient_id = 1 WHERE id = 3;
ALTER TABLE notification ADD FOREIGN KEY (recipient_id) REFERENCES person(id);

-- Notification_type
ALTER TABLE `social_network`.`notification_settings`
CHANGE COLUMN `enable` `enable` BIT;
ALTER TABLE social_network.notification ADD is_deleted BIT(1) NOT NULL DEFAULT 0;

-- Notification_type
UPDATE social_network.notification_type SET name = 'POST_COMMENT' WHERE id = 1;
UPDATE social_network.notification_type SET name = 'COMMENT_COMMENT' WHERE id = 2;
UPDATE social_network.notification_type SET name = 'FRIEND_REQUEST' WHERE id = 3;
INSERT INTO social_network.notification_type (id, code, name) values (4, 40, 'MESSAGE');
INSERT INTO social_network.notification_type (id, code, name) values (5, 50, 'FRIEND_BIRTHDAY');

-- Post_comment
ALTER TABLE `social_network`.`post_comment`
CHANGE COLUMN `parent_id` `parent_id` INT(11) NULL ;
INSERT INTO social_network.post_comment (id, time, post_id, author_id, comment_text, is_blocked, is_deleted) VALUES ('4', '2019-10-22 14:02:00', '3', '3', 'rggfdgd', b'0', b'0');
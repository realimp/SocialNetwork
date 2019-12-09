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
ALTER TABLE block_history DROP FOREIGN KEY block_history_ibfk_3;
ALTER TABLE comment_like DROP FOREIGN KEY comment_like_ibfk_2;
ALTER TABLE notification DROP FOREIGN KEY notification_ibfk_6;
ALTER TABLE post2tag DROP FOREIGN KEY post2tag_ibfk_1;
ALTER TABLE post_comment DROP FOREIGN KEY post_comment_ibfk_3;

ALTER TABLE `social_network`.`post_comment` CHANGE COLUMN `id` `id` INT(11) AUTO_INCREMENT ;

ALTER TABLE block_history ADD FOREIGN KEY (comment_id) REFERENCES post_comment(id);
ALTER TABLE comment_like ADD FOREIGN KEY (comment_id) REFERENCES post_comment(id);
ALTER TABLE notification ADD FOREIGN KEY (entity_id) REFERENCES post_comment(id);
ALTER TABLE post2tag ADD FOREIGN KEY (post_id) REFERENCES post_comment(id);
ALTER TABLE post_comment ADD FOREIGN KEY (parent_id) REFERENCES post_comment(id);

ALTER TABLE `social_network`.`post_comment`
CHANGE COLUMN `parent_id` `parent_id` INT(11) NULL ;
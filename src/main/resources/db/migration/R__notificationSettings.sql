-- CREATING FOREIGN KEYS notification_settings
ALTER TABLE notification_settings ADD FOREIGN KEY (person_id) REFERENCES person(id);

-- Notification_type
ALTER TABLE `social_network`.`notification_settings`
CHANGE COLUMN `enable` `enable` BIT;

-- Notification_type
UPDATE social_network.notification_type SET name = 'POST_COMMENT' WHERE id = 1;
UPDATE social_network.notification_type SET name = 'COMMENT_COMMENT' WHERE id = 2;
UPDATE social_network.notification_type SET name = 'FRIEND_REQUEST' WHERE id = 3;
INSERT INTO social_network.notification_type (id, code, name) values (4, 40, 'MESSAGE');
INSERT INTO social_network.notification_type (id, code, name) values (5, 50, 'FRIEND_BIRTHDAY');

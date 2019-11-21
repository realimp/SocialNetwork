DELETE FROM post2tag;
DELETE FROM tag;

ALTER TABLE post2tag DROP FOREIGN KEY post2tag_ibfk_2;
ALTER TABLE tag CHANGE id id INT AUTO_INCREMENT ;
ALTER TABLE post2tag ADD FOREIGN KEY (tag_id) REFERENCES tag(id);

-- -- Tag
-- INSERT INTO tag (tag) values ('#TAG1');
-- INSERT INTO tag (tag) values ('#TAG2');
-- INSERT INTO tag (tag) values ('#TAG3');
--
-- -- Post2tag
-- INSERT INTO post2tag (post_id, tag_id) values (1, 1);
-- INSERT INTO post2tag (post_id, tag_id) values (2, 2);
-- INSERT INTO post2tag (post_id, tag_id) values (2, 3);
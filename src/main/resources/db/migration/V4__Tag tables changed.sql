DELETE FROM post2tag;
DELETE FROM tag;
ALTER TABLE tag AUTO_INCREMENT=1;
ALTER TABLE post2tag AUTO_INCREMENT=1;

-- -- Tag
-- INSERT INTO tag (tag) values ('#TAG1');
-- INSERT INTO tag (tag) values ('#TAG2');
-- INSERT INTO tag (tag) values ('#TAG3');
--
-- -- Post2tag
-- INSERT INTO post2tag (post_id, tag_id) values (1, 1);
-- INSERT INTO post2tag (post_id, tag_id) values (2, 2);
-- INSERT INTO post2tag (post_id, tag_id) values (2, 3);
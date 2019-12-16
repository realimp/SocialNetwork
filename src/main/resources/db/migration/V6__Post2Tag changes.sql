ALTER TABLE post2tag DROP FOREIGN KEY post2tag_ibfk_3;
ALTER TABLE post2tag ADD FOREIGN KEY (post_id) REFERENCES post(id);
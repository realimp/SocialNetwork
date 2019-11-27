-- CREATING FOREIGN KEYS notification_settings
ALTER TABLE notification_settings ADD FOREIGN KEY (person_id) REFERENCES person(id);

ALTER TABLE notification_settings
ALTER COLUMN enable BIT NOT NULL;

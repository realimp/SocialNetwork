insert into person(id, first_name, last_name, e_mail, phone, password)
values (6, 'testName', 'testLastName', 'test@email.com', '8800200600', '$2a$12$LghzJyLFITIQ3i5dE3H3ZOtbVzVrLSFdZ/vkM66WdaL28yUREOHqO');

UPDATE `social_network`.`person` SET `password` = '$2a$12$LghzJyLFITIQ3i5dE3H3ZOtbVzVrLSFdZ/vkM66WdaL28yUREOHqO';

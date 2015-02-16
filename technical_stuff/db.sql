CREATE TABLE `users` (
  `id_user` INT NOT NULL AUTO_INCREMENT,
  `pseudo_user` VARCHAR(20),
  `passwd_user` VARCHAR(30),
  `mail_user`   VARCHAR(40),
  PRIMARY KEY  (`id_user`)
);

CREATE TABLE `tags` (
  `id_tag` INT NOT NULL AUTO_INCREMENT,
  `id_aggressor` INT,
  `id_victim` INT,
  `tag_name` VARCHAR(30),
  PRIMARY KEY  (`id_tag`)
);

CREATE TABLE `past_events` (
  `id_event` INT NOT NULL AUTO_INCREMENT,
  `id_organizer` INT,
  `lat` FLOAT,
  `lng` FLOAT,
  `description_event` VARCHAR(300),
  `event_name` VARCHAR(60),
  `date_event` TIMESTAMP,
  `timeout` INT,
  `active` BOOLEAN,
  PRIMARY KEY  (`id_event`)
);

CREATE TABLE `junction_user_event` (
  `id_event` INT,
  `id_user` INT
);

CREATE TABLE `junction_event_tag` (
  `id_event` INT,
  `id_tag` INT
);

ALTER TABLE `tags` ADD CONSTRAINT `tags_fk1` FOREIGN KEY (`id_aggressor`) REFERENCES users(`id_user`);
ALTER TABLE `tags` ADD CONSTRAINT `tags_fk2` FOREIGN KEY (`id_victim`) REFERENCES users(`id_user`);
ALTER TABLE `past_events` ADD CONSTRAINT `past_events_fk1` FOREIGN KEY (`id_organizer`) REFERENCES users(`id_user`);
ALTER TABLE `junction_user_event` ADD CONSTRAINT `junction_user_event_fk1` FOREIGN KEY (`id_event`) REFERENCES past_events(`id_event`);
ALTER TABLE `junction_user_event` ADD CONSTRAINT `junction_user_event_fk2` FOREIGN KEY (`id_user`) REFERENCES users(`id_user`);
ALTER TABLE `junction_event_tag` ADD CONSTRAINT `junction_event_tag_fk1` FOREIGN KEY (`id_event`) REFERENCES past_events(`id_event`);
ALTER TABLE `junction_event_tag` ADD CONSTRAINT `junction_event_tag_fk2` FOREIGN KEY (`id_tag`) REFERENCES tags(`id_tag`);

INSERT INTO users(pseudo_user, passwd_user, mail_user) VALUES('EVENT', NULL, NULL);
INSERT INTO users(pseudo_user, passwd_user, mail_user) VALUES('leo', 'mdplol', 'leo@gmail.com');
INSERT INTO users(pseudo_user, passwd_user, mail_user) VALUES('julien le pd', 'je suce des queues', 'grossebitenoire@gmail.com');
INSERT INTO users(pseudo_user, passwd_user, mail_user) VALUES('quentin', 'lol', 'test@gmail.com');
INSERT INTO users(pseudo_user, passwd_user, mail_user) VALUES('loris', 'poilmdr', 'test_lol@gmail.com');

INSERT INTO tags(tag_name, id_aggressor, id_victim) VALUES('enculer', 2, 3);
INSERT INTO tags(tag_name, id_aggressor, id_victim) VALUES('trop lol', 3, 2);
INSERT INTO tags(tag_name, id_aggressor, id_victim) VALUES('football', 1, 1);
INSERT INTO tags(tag_name, id_aggressor, id_victim) VALUES('lollyagging', 1, 1);
INSERT INTO tags(tag_name, id_aggressor, id_victim) VALUES('poilu', 5, 4);
INSERT INTO tags(tag_name, id_aggressor, id_victim) VALUES('alcoolique', 4, 5);

INSERT INTO past_events(id_organizer, lat, lng, description_event, event_name, date_event, timeout, active)
	   VALUES(2, 41.25, -120.9762, 'a liitle football game', 'foot au hasard', NULL, NULL, false);
INSERT INTO past_events(id_organizer, lat, lng, description_event, event_name, date_event, timeout, active))
	   VALUES(3, 48.582933, 7.743749, 'where is charlie', 'marche citoyenne', NULL, NULL, false);
INSERT INTO past_events(id_organizer, lat, lng, description_event, event_name, date_event, timeout, active))
	   VALUES(4, 48.581547, 7.73909, 'lollygagging', 'skyrim playing', NULL, NULL, false);
INSERT INTO past_events(id_organizer, lat, lng, description_event, event_name, date_event, timeout, active))
	   VALUES(5, 48.58186, 7.742872, 'lolpoiltest', 'some bs', NULL, NULL, false);

INSERT INTO junction_user_event(id_event, id_user) VALUES(1, 2);
INSERT INTO junction_user_event(id_event, id_user) VALUES(1, 3);
INSERT INTO junction_user_event(id_event, id_user) VALUES(1, 4);
INSERT INTO junction_user_event(id_event, id_user) VALUES(1, 5);
INSERT INTO junction_user_event(id_event, id_user) VALUES(2, 2);
INSERT INTO junction_user_event(id_event, id_user) VALUES(2, 5);
INSERT INTO junction_user_event(id_event, id_user) VALUES(3, 3);
INSERT INTO junction_user_event(id_event, id_user) VALUES(3, 4);
INSERT INTO junction_user_event(id_event, id_user) VALUES(3, 2);
INSERT INTO junction_user_event(id_event, id_user) VALUES(4, 4);
INSERT INTO junction_user_event(id_event, id_user) VALUES(4, 5);
INSERT INTO junction_user_event(id_event, id_user) VALUES(4, 3);

INSERT INTO junction_event_tag(id_event, id_tag) VALUES(1, 3);
INSERT INTO junction_event_tag(id_event, id_tag) VALUES(2, 4);
INSERT INTO junction_event_tag(id_event, id_tag) VALUES(3, 3);
INSERT INTO junction_event_tag(id_event, id_tag) VALUES(4, 4);

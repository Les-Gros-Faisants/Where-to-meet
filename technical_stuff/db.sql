CREATE TABLE `users` (
  `id_user` INT NOT NULL AUTO_INCREMENT,
  `pseudo_user` VARCHAR(20),
  `passwd_user` VARCHAR(30),
  PRIMARY KEY  (`id_user`)
);

CREATE TABLE `tags` (
  `id_tag` INT NOT NULL AUTO_INCREMENT,
  `id_aggressor` INT,
  `id_victim` INT,
  PRIMARY KEY  (`id_tag`)
);

CREATE TABLE `past_events` (
  `id_event` INT NOT NULL AUTO_INCREMENT,
  `id_organizer` INT,
  `geolocation` VARCHAR(50),
  `description_event` VARCHAR(300),
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

create TABLE `card` (
	`id` BIGINT NOT NULL AUTO_INCREMENT,
	`title` VARCHAR(120),
	`expire_at` DATETIME,
	PRIMARY KEY (`id`)
);
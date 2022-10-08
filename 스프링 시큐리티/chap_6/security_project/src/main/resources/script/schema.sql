CREATE TABLE IF NOT EXISTS `security_project`.`user` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(45) NOT NULL,
    `password` TEXT NOT NULL,
    `algorithm` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `security_project`.`authority` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    `user` INT NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `security_project`.`product` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    `price` INT NOT NULL,
    `currency` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
);

INSERT IGNORE INTO `security_project`.`user` (`id`, `username`, `password`, `algorithm`)
    VALUES ('1', 'sim', '$2a$10$1FqrvFifY/QiI7wMqlBSvu9TTVakh2f0eEEqAEumVVdEw1D.ko5ry', 'BCRYPT');

INSERT IGNORE INTO `security_project`.`authority` (`id`, `name` `user`) VALUES ('1', 'READ', '1');
INSERT IGNORE INTO `security_project`.`authority` (`id`, `name` `user`) VALUES ('2', 'READ', '1');
INSERT IGNORE INTO `security_project`.`product` (`id`, `name` `price`, `currency`) VALUES ('1', 'Chocolate', '10', 'USD');

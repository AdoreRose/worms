CREATE DATABASE IF NOT EXISTS `Worms` DEFAULT CHARSET 'utf8';

USE `TopPlayer`;

CREATE TABLE IF NOT EXISTS `data` (
    `key`       enum('nick', 'score') not null,
    `value`     varchar(15)             not null,
    UNIQUE (`key`)
)
ENGINE = InnoDB;


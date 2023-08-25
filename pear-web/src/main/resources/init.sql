CREATE TABLE `t_account`
(
    `id`      int         NOT NULL AUTO_INCREMENT,
    `age`     tinyint     DEFAULT NULL,
    `email`   varchar(50) DEFAULT NULL,
    `name`    varchar(50) NOT NULL,
    `sex`     tinyint     DEFAULT NULL,
    `version` int         DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_lx20ruvop4jwfl5qul6insji4` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
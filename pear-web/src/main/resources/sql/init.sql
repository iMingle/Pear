CREATE TABLE `t_account` (
  `id` bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `age` int(11) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `name` varchar(30) NOT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  UNIQUE KEY `UK_lx20ruvop4jwfl5qul6insji4` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

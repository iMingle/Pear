CREATE TABLE `t_account` (
  `id` bigint(20) NOT NULL,
  `age` int(11) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `name` varchar(30) NOT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_lx20ruvop4jwfl5qul6insji4` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

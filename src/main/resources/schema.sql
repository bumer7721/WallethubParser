DROP TABLE IF EXISTS `log_record`;

CREATE TABLE `log_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `request_date` datetime DEFAULT NULL,
  `ip` varchar(15) DEFAULT NULL,
  `request` varchar(45) DEFAULT NULL,
  `status` smallint(6) DEFAULT NULL,
  `user_agent` text,
  PRIMARY KEY (`id`)
);
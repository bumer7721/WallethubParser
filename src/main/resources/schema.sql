DROP TABLE IF EXISTS `log_record`;
DROP TABLE IF EXISTS `blocked_ip`;

CREATE TABLE `log_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `request_date` datetime DEFAULT NULL,
  `ip` varchar(15) DEFAULT NULL,
  `request` varchar(45) DEFAULT NULL,
  `status` smallint(6) DEFAULT NULL,
  `user_agent` text,
  PRIMARY KEY (`id`)
);

CREATE TABLE `blocked_ip` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(15) DEFAULT NULL,
  `comment` text,
  PRIMARY KEY (`id`)
);
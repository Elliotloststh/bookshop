CREATE TABLE `record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sender_id` bigint(20) DEFAULT NULL,
  `receiver_id` bigint(20) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `time` bigint(20) DEFAULT NULL,
  `is_read` int(11) DEFAULT NULL COMMENT '0为未读，1为已读',
  PRIMARY KEY (`id`),
  KEY `idx_sender_receiver` (`sender_id`,`receiver_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
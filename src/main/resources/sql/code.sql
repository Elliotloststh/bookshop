CREATE TABLE `code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `type` int(11) DEFAULT NULL COMMENT '0为注册验证码',
  `code` varchar(16) DEFAULT NULL,
  `create_time` int(11) DEFAULT '0',
  `update_time` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
create index `idx_email_type` on code(email, type);
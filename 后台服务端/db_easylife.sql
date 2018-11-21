-- phpMyAdmin SQL Dump
-- version 2.10.3
-- http://www.phpmyadmin.net
-- 
-- 主机: localhost
-- 生成日期: 2018 年 04 月 04 日 05:12
-- 服务器版本: 5.0.51
-- PHP 版本: 5.2.6

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

-- 
-- 数据库: `db_easylife`
-- 

-- --------------------------------------------------------

-- 
-- 表的结构 `help`
-- 

CREATE TABLE `help` (
  `help_id` int(11) NOT NULL auto_increment,
  `accept_date` datetime default NULL,
  `accept_user_id` int(11) default NULL,
  `classname` varchar(255) default NULL,
  `complete_date` datetime default NULL,
  `content` varchar(255) default NULL,
  `money` float NOT NULL,
  `pay_date` datetime default NULL,
  `release_date` datetime default NULL,
  `status` int(11) default NULL,
  `title` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL,
  `images_url` varchar(255) default NULL,
  `credit` float NOT NULL,
  `sex_limit` int(11) default NULL,
  PRIMARY KEY  (`help_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=49 ;

-- 
-- 导出表中的数据 `help`
-- 

INSERT INTO `help` VALUES (48, '2018-04-04 12:43:32', 2, '代拿', '2018-04-04 12:44:51', '菜鸟驿站', 1, '2018-04-04 12:45:30', '2018-04-03 20:37:44', 3, '拿快递', 3, 'helpImage/fe13e8c1-3600-4a9a-9456-5f59627737bfaa.jpg', 131, 0);

-- --------------------------------------------------------

-- 
-- 表的结构 `message`
-- 

CREATE TABLE `message` (
  `id` int(11) NOT NULL,
  `date` datetime default NULL,
  `form_user_id` int(11) NOT NULL,
  `msg` varchar(255) default NULL,
  `to_user_id` int(11) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- 导出表中的数据 `message`
-- 


-- --------------------------------------------------------

-- 
-- 表的结构 `user`
-- 

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `birthday` varchar(50) default NULL,
  `cance_number` int(11) default NULL,
  `credit` float NOT NULL,
  `grade` varchar(255) default NULL,
  `head_image` varchar(255) default NULL,
  `hometown` varchar(255) default NULL,
  `lack` bit(1) NOT NULL,
  `major` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `notes` varchar(255) default NULL,
  `number` varchar(255) default NULL,
  `phone_number` varchar(255) default NULL,
  `school` varchar(255) default NULL,
  `sex` varchar(255) default NULL,
  `signature` varchar(255) default NULL,
  PRIMARY KEY  (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- 导出表中的数据 `user`
-- 

INSERT INTO `user` VALUES (16, '2008-4-3', 0, 100, NULL, 'headImage/gsw.jpg', '中国', '\0', NULL, '耿守卫', NULL, NULL, '15251805878', '南京晓庄学院', '男', '我爱你');
INSERT INTO `user` VALUES (2, '0', 1, 227, NULL, 'headImage/xgg.jpg', NULL, '', '网络工程', '张鑫', NULL, '15131125', '17721570251', '南京晓庄学院', '保密', NULL);
INSERT INTO `user` VALUES (3, '1997-7-13', 0, 134, NULL, 'headImage/lk.jpg', '宿迁', '', '网络工程', '刘康', NULL, '15131112', '18021396096', '南京晓庄学院', '男', '加油');
INSERT INTO `user` VALUES (0, '2018-4-1', 0, 999, NULL, 'headImage/logo.png', '南京', '', NULL, 'HEO', NULL, NULL, '999999999', '南京晓庄学院', '保密', '我是easylife管理员');
INSERT INTO `user` VALUES (17, '2011-4-3', 0, 100, NULL, 'headImage/cdad5e85-2da9-4e86-a679-fb3f07ac6e4e1.jpg', 'ak', '\0', '网络工程', '秦星星', NULL, '15131115', '13851425164', '南京晓庄学院', '男', '47');

-- --------------------------------------------------------

-- 
-- 表的结构 `user_login`
-- 

CREATE TABLE `user_login` (
  `user_id` int(11) NOT NULL auto_increment,
  `status` int(11) default NULL,
  `password` varchar(16) NOT NULL,
  `phone_number` varchar(11) NOT NULL,
  PRIMARY KEY  (`user_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=18 ;

-- 
-- 导出表中的数据 `user_login`
-- 

INSERT INTO `user_login` VALUES (16, NULL, 'gsw1121', '15251805878');
INSERT INTO `user_login` VALUES (2, NULL, 'zz17721570251', '17721570251');
INSERT INTO `user_login` VALUES (3, NULL, 'lk15131112.', '18021396096');
INSERT INTO `user_login` VALUES (8, NULL, 'wdfyy3566', '51554444555');
INSERT INTO `user_login` VALUES (7, NULL, '33677&e', '12225444552');
INSERT INTO `user_login` VALUES (9, NULL, 's236778', '12455524555');
INSERT INTO `user_login` VALUES (10, NULL, '123456a', '12345678901');
INSERT INTO `user_login` VALUES (17, NULL, '123456qq', '13851425164');

-- --------------------------------------------------------

-- 
-- 表的结构 `vcode`
-- 

CREATE TABLE `vcode` (
  `phone_number` varchar(255) NOT NULL,
  `expiry` datetime default NULL,
  `v_code` varchar(255) NOT NULL,
  PRIMARY KEY  (`phone_number`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- 导出表中的数据 `vcode`
-- 


-- --------------------------------------------------------

-- 
-- 表的结构 `wallet`
-- 

CREATE TABLE `wallet` (
  `user_id` int(11) NOT NULL,
  `money` float NOT NULL,
  `pay_password` varchar(255) default NULL,
  PRIMARY KEY  (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- 导出表中的数据 `wallet`
-- 

INSERT INTO `wallet` VALUES (2, 12.4, '123456');
INSERT INTO `wallet` VALUES (3, 64.7, '731218');
INSERT INTO `wallet` VALUES (17, 50, '123456');
INSERT INTO `wallet` VALUES (0, 9999, '123123');
INSERT INTO `wallet` VALUES (16, 70, '939688');

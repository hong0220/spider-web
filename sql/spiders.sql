/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50621
Source Host           : localhost:3306
Source Database       : spiders

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2015-03-20 21:39:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bbs
-- ----------------------------
DROP TABLE IF EXISTS `bbs`;
CREATE TABLE `bbs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(125) DEFAULT NULL,
  `time` varchar(20) DEFAULT NULL,
  `source` varchar(50) DEFAULT NULL,
  `content` text,
  `http` varchar(365) DEFAULT NULL,
  `commentSum` int(11) DEFAULT '20',
  PRIMARY KEY (`id`),
  KEY `time` (`time`),
  KEY `newsup` (`time`) USING BTREE,
  KEY `newscollect` (`time`),
  KEY `id` (`id`,`time`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ipagent
-- ----------------------------
DROP TABLE IF EXISTS `ipagent`;
CREATE TABLE `ipagent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(15) DEFAULT NULL,
  `port` int(5) DEFAULT NULL,
  `can` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(125) DEFAULT NULL,
  `time` varchar(20) DEFAULT NULL,
  `source` varchar(50) DEFAULT NULL,
  `content` text,
  `http` varchar(365) DEFAULT NULL,
  `commentSum` int(11) DEFAULT '20',
  PRIMARY KEY (`id`),
  KEY `time` (`time`),
  KEY `newsup` (`time`) USING BTREE,
  KEY `newscollect` (`time`),
  KEY `id` (`id`,`time`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for search
-- ----------------------------
DROP TABLE IF EXISTS `search`;
CREATE TABLE `search` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(125) DEFAULT NULL,
  `content` varchar(365) DEFAULT NULL,
  `time` varchar(20) DEFAULT NULL,
  `http` varchar(365) DEFAULT NULL,
  `source` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for weibo
-- ----------------------------
DROP TABLE IF EXISTS `weibo`;
CREATE TABLE `weibo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `weibo_id` varchar(50) DEFAULT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `content` varchar(365) DEFAULT NULL,
  `source` varchar(50) DEFAULT NULL,
  `forwards` int(11) DEFAULT NULL,
  `comments` int(11) DEFAULT NULL,
  `http` varchar(500) DEFAULT NULL,
  `page` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for weibo_comment
-- ----------------------------
DROP TABLE IF EXISTS `weibo_comment`;
CREATE TABLE `weibo_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cid` varchar(50) DEFAULT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  `content` varchar(365) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `source` varchar(50) DEFAULT NULL,
  `weiboid` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for weibo_search
-- ----------------------------
DROP TABLE IF EXISTS `weibo_search`;
CREATE TABLE `weibo_search` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` varchar(50) DEFAULT NULL,
  `time` timestamp NULL DEFAULT NULL,
  `content` text,
  `source` varchar(50) DEFAULT NULL,
  `key_word` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for weixin
-- ----------------------------
DROP TABLE IF EXISTS `weixin`;
CREATE TABLE `weixin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `source` varchar(50) DEFAULT NULL,
  `content` text,
  `http` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

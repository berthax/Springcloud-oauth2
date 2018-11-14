/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : authcenter

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2018-11-14 09:18:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for client
-- ----------------------------
DROP TABLE IF EXISTS `client`;
CREATE TABLE `client` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `client_id` varchar(255) NOT NULL COMMENT '（必须的）用来标识客户的Id。',
  `secret` varchar(255) NOT NULL COMMENT '（需要值得信任的客户端）客户端安全码，如果有的话。',
  `scope` varchar(255) DEFAULT NULL COMMENT '用来限制客户端的访问范围，如果为空（默认）的话，那么客户端拥有全部的访问范围。',
  `authorized_grant_types` varchar(255) DEFAULT NULL COMMENT '此客户端可以使用的授权类型，默认为空。',
  `authorities` varchar(255) DEFAULT NULL COMMENT '此客户端可以使用的权限（基于Spring Security authorities）',
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modify` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of client
-- ----------------------------
INSERT INTO `client` VALUES ('1', 'hao', '111111', null, null, null, null, null);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nick` varchar(255) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modify` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'user_1', '$2a$10$3f72RNjlgxLvDEJ8Qz/mHOxMxCZTCX3Mv6AMMXaQfBoLhux5z4.d.', null, null, null, '2018-09-26 16:56:00', null);

/*
Navicat MySQL Data Transfer

Source Server         : 246
Source Server Version : 50620
Source Host           : 192.168.1.246:4051
Source Database       : education_biz

Target Server Type    : MYSQL
Target Server Version : 50620
File Encoding         : 65001

Date: 2015-04-21 10:04:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for grade_subject
-- ----------------------------
DROP TABLE IF EXISTS `grade_subject`;
CREATE TABLE `grade_subject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `grade_id` int(11) NOT NULL COMMENT '年级id',
  `subject_id` int(11) NOT NULL COMMENT '学科id',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='年级与学科对应';

-- ----------------------------
-- Records of grade_subject
-- ----------------------------
INSERT INTO `grade_subject` VALUES ('3', '1', '1', '2015-04-16 10:59:49', '2015-04-16 10:59:49');
INSERT INTO `grade_subject` VALUES ('5', '1', '2', '2015-04-16 10:59:50', '2015-04-16 10:59:50');
INSERT INTO `grade_subject` VALUES ('7', '1', '3', '2015-04-16 10:59:50', '2015-04-16 10:59:50');
INSERT INTO `grade_subject` VALUES ('9', '2', '1', '2015-04-16 10:59:51', '2015-04-16 10:59:51');
INSERT INTO `grade_subject` VALUES ('11', '2', '2', '2015-04-16 10:59:51', '2015-04-16 10:59:51');
INSERT INTO `grade_subject` VALUES ('13', '2', '3', '2015-04-16 10:59:51', '2015-04-16 10:59:51');
INSERT INTO `grade_subject` VALUES ('15', '2', '4', '2015-04-16 10:59:51', '2015-04-16 10:59:51');
INSERT INTO `grade_subject` VALUES ('17', '2', '5', '2015-04-16 10:59:52', '2015-04-16 10:59:52');
INSERT INTO `grade_subject` VALUES ('19', '2', '6', '2015-04-16 10:59:52', '2015-04-16 10:59:52');
INSERT INTO `grade_subject` VALUES ('21', '2', '7', '2015-04-16 10:59:52', '2015-04-16 10:59:52');
INSERT INTO `grade_subject` VALUES ('23', '2', '8', '2015-04-16 10:59:52', '2015-04-16 10:59:52');
INSERT INTO `grade_subject` VALUES ('25', '2', '9', '2015-04-16 10:59:53', '2015-04-16 10:59:53');
INSERT INTO `grade_subject` VALUES ('27', '3', '1', '2015-04-16 10:59:53', '2015-04-16 10:59:53');
INSERT INTO `grade_subject` VALUES ('29', '3', '2', '2015-04-16 10:59:53', '2015-04-16 10:59:53');
INSERT INTO `grade_subject` VALUES ('31', '3', '3', '2015-04-16 10:59:53', '2015-04-16 10:59:53');
INSERT INTO `grade_subject` VALUES ('33', '3', '4', '2015-04-16 10:59:54', '2015-04-16 10:59:54');
INSERT INTO `grade_subject` VALUES ('35', '3', '5', '2015-04-16 10:59:54', '2015-04-16 10:59:54');
INSERT INTO `grade_subject` VALUES ('37', '3', '6', '2015-04-16 10:59:54', '2015-04-16 10:59:54');
INSERT INTO `grade_subject` VALUES ('39', '3', '7', '2015-04-16 10:59:54', '2015-04-16 10:59:54');
INSERT INTO `grade_subject` VALUES ('41', '3', '8', '2015-04-16 10:59:55', '2015-04-16 10:59:55');
INSERT INTO `grade_subject` VALUES ('43', '3', '9', '2015-04-16 10:59:55', '2015-04-16 10:59:55');

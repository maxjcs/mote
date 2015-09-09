/*
Navicat MySQL Data Transfer

Source Server         : 246
Source Server Version : 50620
Source Host           : 192.168.1.246:4051
Source Database       : education_biz

Target Server Type    : MYSQL
Target Server Version : 50620
File Encoding         : 65001

Date: 2015-04-21 10:03:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for subject
-- ----------------------------
DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '学科名称',
  `real_subject` tinyint(4) NOT NULL COMMENT '学科编号',
  `sort_number` bigint(20) NOT NULL COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='学科表';

-- ----------------------------
-- Records of subject
-- ----------------------------
INSERT INTO `subject` VALUES ('1', '数学', '1', '1', '2014-12-05 13:29:56', '2014-12-05 13:29:56');
INSERT INTO `subject` VALUES ('2', '语文', '2', '2', '2014-12-05 13:29:56', '2014-12-05 13:29:56');
INSERT INTO `subject` VALUES ('3', '英语', '3', '3', '2014-12-05 13:29:56', '2014-12-05 13:29:56');
INSERT INTO `subject` VALUES ('4', '政治', '4', '4', '2014-12-05 13:29:56', '2014-12-05 13:29:56');
INSERT INTO `subject` VALUES ('5', '历史', '5', '5', '2014-12-05 13:29:56', '2014-12-05 13:29:56');
INSERT INTO `subject` VALUES ('6', '地理', '6', '6', '2014-12-05 13:29:56', '2014-12-05 13:29:56');
INSERT INTO `subject` VALUES ('7', '物理', '7', '7', '2014-12-05 13:29:56', '2014-12-05 13:29:56');
INSERT INTO `subject` VALUES ('8', '化学', '8', '8', '2014-12-05 13:29:56', '2014-12-05 13:29:56');
INSERT INTO `subject` VALUES ('9', '生物', '9', '9', '2014-12-05 13:29:56', '2014-12-05 13:29:56');

/*
Navicat MySQL Data Transfer

Source Server         : 246
Source Server Version : 50620
Source Host           : 192.168.1.246:4051
Source Database       : education_biz

Target Server Type    : MYSQL
Target Server Version : 50620
File Encoding         : 65001

Date: 2015-04-21 10:04:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for grade
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '年级名称',
  `sort_number` bigint(20) NOT NULL COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='年级表';

-- ----------------------------
-- Records of grade
-- ----------------------------
INSERT INTO `grade` VALUES ('1', '小学', '1', '2014-12-05 13:30:04', '2014-12-05 13:30:04');
INSERT INTO `grade` VALUES ('2', '初中', '2', '2014-12-05 13:30:05', '2014-12-05 13:30:05');
INSERT INTO `grade` VALUES ('3', '高中', '3', '2014-12-05 13:30:05', '2014-12-05 13:30:05');

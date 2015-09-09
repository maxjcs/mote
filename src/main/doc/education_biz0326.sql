-- MySQL dump 10.13  Distrib 5.6.20-68.0, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: education_biz
-- ------------------------------------------------------
-- Server version	5.6.20-68.0-56-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `audio_approve`
--

DROP TABLE IF EXISTS `audio_approve`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audio_approve` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `audio_id` varchar(50) NOT NULL COMMENT '音频id',
  `star` tinyint(4) NOT NULL COMMENT '星级',
  `approvor` varchar(50) NOT NULL COMMENT '审核人',
  `approve_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '审核时间',
  `reason` text COMMENT '审核不通过原因',
  `status` tinyint(4) NOT NULL COMMENT '审核状态 0待审 1通过 2不通过',
  PRIMARY KEY (`id`),
  KEY `idx_audio_approve_question_id` (`audio_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1058452 DEFAULT CHARSET=utf8 COMMENT='音频审核表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `audio_quest_count`
--

DROP TABLE IF EXISTS `audio_quest_count`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audio_quest_count` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `question_id` bigint(20) NOT NULL COMMENT '题目id',
  `count` int(11) NOT NULL DEFAULT '0' COMMENT '每个音频的请求总数',
  `version` bigint(20) NOT NULL COMMENT '时间戳版本',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '记录状态',
  PRIMARY KEY (`id`),
  KEY `idx_audio_quest_count_version` (`version`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=131947 DEFAULT CHARSET=utf8 COMMENT='音频请求统计表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `audio_quest_detail`
--

DROP TABLE IF EXISTS `audio_quest_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audio_quest_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(50) NOT NULL COMMENT '用户id',
  `question_id` bigint(50) NOT NULL COMMENT '问题id',
  `image_id` varchar(50) NOT NULL COMMENT 'imageid',
  `status` tinyint(4) NOT NULL COMMENT '记录状态',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_audio_quest_detail_question_id` (`question_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=138886 DEFAULT CHARSET=utf8 COMMENT='音频请求表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `audio_upload`
--

DROP TABLE IF EXISTS `audio_upload`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audio_upload` (
  `id` varchar(50) NOT NULL COMMENT '音频id',
  `question_id` bigint(20) NOT NULL COMMENT '题目id',
  `duration` int(11) DEFAULT NULL COMMENT '秒数',
  `name` varchar(100) DEFAULT NULL COMMENT '音频名称',
  `url` varchar(100) NOT NULL COMMENT '音频url',
  `subject` int(11) DEFAULT NULL COMMENT '学科',
  `status` tinyint(4) DEFAULT NULL COMMENT '音频状态，0待审 1审核通过 2审核不通过',
  `approve_time` datetime DEFAULT NULL COMMENT '审核时间',
  `teacher_id` varchar(50) DEFAULT NULL COMMENT '教师id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_audio_teacher_id` (`teacher_id`) USING BTREE,
  KEY `idx_audio_create_time` (`create_time`) USING BTREE,
  KEY `idx_audio_question_id` (`question_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='音频上传表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bak_audio_quest_detail`
--

DROP TABLE IF EXISTS `bak_audio_quest_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bak_audio_quest_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(50) NOT NULL COMMENT '用户id',
  `question_id` bigint(50) NOT NULL COMMENT '问题id',
  `image_id` varchar(50) NOT NULL COMMENT 'imageid',
  `status` tinyint(4) NOT NULL COMMENT '记录状态',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=137254 DEFAULT CHARSET=utf8 COMMENT='音频请求表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `city` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '城市名称',
  `sort_number` bigint(20) NOT NULL COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `pid` bigint(20) DEFAULT '0' COMMENT '父id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_city_name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8 COMMENT='城市表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `error_correction`
--

DROP TABLE IF EXISTS `error_correction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `error_correction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `question_id` int(10) NOT NULL COMMENT '题目id',
  `teacher_id` varchar(50) NOT NULL COMMENT '纠错教师id',
  `reason` text COMMENT '纠错理由',
  `check_status` tinyint(4) NOT NULL COMMENT '审核状态 1已审 0待审',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_error_correction_question_id` (`question_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20224 DEFAULT CHARSET=utf8 COMMENT='题目纠错表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `grade`
--

DROP TABLE IF EXISTS `grade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grade` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '年级名称',
  `sort_number` bigint(20) NOT NULL COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='年级表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `subject` varchar(200) DEFAULT NULL COMMENT '科目',
  `latex` text COMMENT '标题',
  `content` text COMMENT '试题内容',
  `knowledge` varchar(200) DEFAULT NULL COMMENT '知识点',
  `answer` text COMMENT '试题答案',
  `solution` text COMMENT '试题解析',
  `learn_phase` varchar(200) DEFAULT NULL COMMENT '年级',
  `real_subject` tinyint(4) NOT NULL COMMENT '学科编号',
  `real_id` bigint(20) NOT NULL COMMENT '来源id',
  `error_number` int(10) DEFAULT '0' COMMENT '纠错次数',
  `record_number` int(10) DEFAULT '0' COMMENT '纠错次数',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `emergency_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '紧急状态 0不紧急 1紧急',
  `emergency_count` int(11) NOT NULL DEFAULT '0',
  `allot_count` tinyint(4) NOT NULL DEFAULT '0' COMMENT '问题分配的人数',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `audio_upload_status` tinyint(4) NOT NULL DEFAULT '-1' COMMENT '音频上传的审核状态，-1：没有录制，0：待审 1：审核通过 2：审核不通过',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_question_real_id` (`real_id`),
  KEY `idx_real_sub` (`real_subject`),
  KEY `idx_em_audi_all` (`emergency_status`,`audio_upload_status`,`allot_count`)
) ENGINE=InnoDB AUTO_INCREMENT=3198679 DEFAULT CHARSET=utf8 COMMENT='热题表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `question_allot`
--

DROP TABLE IF EXISTS `question_allot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question_allot` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `question_id` bigint(20) NOT NULL COMMENT '??id',
  `allot_user` varchar(50) NOT NULL COMMENT '???',
  `allot_time` datetime NOT NULL COMMENT '????',
  `status` tinyint(4) NOT NULL COMMENT '???? 1?? 0???',
  PRIMARY KEY (`id`),
  KEY `idx_question_allot_allot_time` (`allot_time`) USING BTREE,
  KEY `idx_question_allot_allot_user` (`allot_user`),
  KEY `idx_question_allot_question` (`question_id`,`allot_user`,`status`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=7080082 DEFAULT CHARSET=utf8 COMMENT='?????';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `school`
--

DROP TABLE IF EXISTS `school`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `school` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `city_id` int(10) NOT NULL COMMENT '城市id',
  `name` varchar(50) NOT NULL COMMENT '学校名称',
  `sort_number` bigint(20) NOT NULL COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_school_name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=271 DEFAULT CHARSET=utf8 COMMENT='学校表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `subject`
--

DROP TABLE IF EXISTS `subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subject` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '学科名称',
  `real_subject` tinyint(4) NOT NULL COMMENT '学科编号',
  `sort_number` bigint(20) NOT NULL COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='学科表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `system_config`
--

DROP TABLE IF EXISTS `system_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `key_name` varchar(50) NOT NULL COMMENT 'key',
  `value_name` varchar(200) NOT NULL COMMENT 'value',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_sys_config_key_name` (`key_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=49852 DEFAULT CHARSET=utf8 COMMENT='系统变量表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teacher` (
  `id` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '教师姓名',
  `phone_number` varchar(20) NOT NULL COMMENT '手机号',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `id_number` varchar(20) NOT NULL COMMENT '身份证号',
  `qq` varchar(20) NOT NULL COMMENT 'QQ号',
  `school_id` int(10) NOT NULL COMMENT '学校id',
  `id_card_image_url` varchar(200) NOT NULL COMMENT '身份证照片url',
  `student_card_image_url` varchar(200) NOT NULL COMMENT '学生证照片url',
  `status` tinyint(4) NOT NULL COMMENT '账户状态 1有效 0无效',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `province_id` int(11) DEFAULT '0' COMMENT '省份id',
  `city_id` int(11) DEFAULT '0' COMMENT '城市id',
  `weixin` varchar(20) DEFAULT '' COMMENT '微信',
  `subjects` varchar(100) DEFAULT '' COMMENT '可教授学科',
  `grades` varchar(100) DEFAULT '' COMMENT '可教授年级',
  `bank_card` varchar(50) DEFAULT '' COMMENT '银行卡',
  `bank` varchar(100) DEFAULT '' COMMENT '开户银行',
  `alipay` varchar(100) DEFAULT '' COMMENT '支付宝账号',
  `course_time` varchar(100) DEFAULT '' COMMENT '可授课时间',
  `self_description` varchar(100) DEFAULT '' COMMENT '自我描述',
  `course_year` int(11) DEFAULT '0' COMMENT '教龄',
  `course_area` varchar(100) DEFAULT '' COMMENT '可教授地区',
  `mingshihui` tinyint(4) DEFAULT '0' COMMENT '名师汇老师',
  `xingjihua` tinyint(4) DEFAULT '1' COMMENT '星计划老师',
  `operater` varchar(50) DEFAULT '' COMMENT '操作者',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_teacher_phone_number` (`phone_number`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='教师用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teacher_choose`
--

DROP TABLE IF EXISTS `teacher_choose`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teacher_choose` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `teacher_id` varchar(50) DEFAULT NULL COMMENT '教师id',
  `learn_phase` varchar(200) DEFAULT NULL COMMENT '年级',
  `real_subject` tinyint(4) DEFAULT NULL COMMENT '学科编号',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_teacher_choose_teacher_id` (`teacher_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1821046 DEFAULT CHARSET=utf8 COMMENT='教师筛选记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `verify_codes`
--

DROP TABLE IF EXISTS `verify_codes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `verify_codes` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `phone_number` varchar(20) NOT NULL COMMENT '手机号',
  `verify_code` varchar(20) NOT NULL COMMENT '验证码',
  `send_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_verify_codes_phone_number` (`phone_number`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=107008 DEFAULT CHARSET=utf8 COMMENT='验证码表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'education_biz'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-03-26 11:03:03

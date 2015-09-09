drop table if exists common_config;
CREATE TABLE `common_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `config_key` varchar(256) NOT NULL DEFAULT '' COMMENT 'key值，该字段唯一',
  `config_value` int(11) NOT NULL COMMENT 'key对应的数值',
  `description` varchar(512) DEFAULT NULL COMMENT '该key－value的描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='通用配置key_value表';

INSERT INTO `common_config` (`id`, `config_key`, `config_value`, `description`)
VALUES
	(1, 'audio_sales_info_teacher', 50, '教师音频销售分成，value为百分比'),
	(2, 'everyday_task_answer_num', 11, '每日任务录题数量'),
	(3, 'complete_task_reward', 21, '完成每日任务返回积分数'),
	(4, 'audio_sales_info_student', 0, '学生音频销售分成，value为百分比');


drop table if exists teacher_function;
CREATE TABLE `teacher_function` (
  `id` varchar(50) NOT NULL DEFAULT '' COMMENT 'teacherId teacher表主键id',
  `everyday_task` tinyint(4) DEFAULT '1' COMMENT '每日任务功能模块，1:可用，0:不可用',
  `feud` tinyint(4) DEFAULT '1' COMMENT '抢答功能模块，1:可用，0:不可用',
  `learn_talk` tinyint(4) DEFAULT '1' COMMENT '互动学习功能模块，1:可用，0:不可用',
  `pay` tinyint(4) DEFAULT '1' COMMENT '支付模块，1:可用，0:不可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='教师拥有的功能配置表';


<<<<<<< HEAD
CREATE TABLE `organization_sources` (
`id`  bigint(80) NOT NULL AUTO_INCREMENT COMMENT '主键' ,
`organization_name`  varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '来源机构名' ,
`status`  int(2) NULL DEFAULT 0 COMMENT '0:--正常，---1 停用' ,
`operator`  varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人' ,
`create_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`),
INDEX `id_organization_name_idx` (`id`, `organization_name`) USING BTREE ,
INDEX `id_status_idx` (`id`, `status`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=5
ROW_FORMAT=COMPACT
;




教师端后台提供，  biz库
题目关联机构表，录题后台会定时插入线上题目与机构id相关联的表内


以下是录题后台使用的关联表
orc_ops_sources  biz库
CREATE TABLE `orc_ops_sources` (
`id`  bigint(80) NOT NULL AUTO_INCREMENT COMMENT '主键' ,
`orc_picture_id`  bigint(80) NULL DEFAULT NULL COMMENT '识别图片id' ,
`bookid`  bigint(80) NULL DEFAULT NULL COMMENT '图书id' ,
`publishing_house_id`  bigint(80) NULL DEFAULT NULL COMMENT '机构来源id' ,
`tran_ops_id`  bigint(80) NULL DEFAULT NULL COMMENT '运营后台录入题目id' ,
`questionid`  bigint(80) NULL DEFAULT NULL COMMENT '线上题目id' ,
`status`  int(2) NULL DEFAULT 0 COMMENT '0:未完成线上对应状态 1：完成对应状态' ,
`operator`  varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人' ,
`create_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间' ,
PRIMARY KEY (`id`),
INDEX `id_tran_ops_id_idx` (`id`, `tran_ops_id`, `status`) USING BTREE ,
INDEX `questionid_publishing_house_id_idx` (`publishing_house_id`, `questionid`, `status`) USING BTREE ,
INDEX `id` (`id`, `orc_picture_id`, `bookid`, `publishing_house_id`, `status`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;


drop table if exists system_user;
CREATE TABLE `system_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_account` varchar(100) NOT NULL DEFAULT '' COMMENT '登陆用户名',
  `password` varchar(100) NOT NULL DEFAULT '' COMMENT '登陆密码',
  `name` varchar(100) DEFAULT NULL COMMENT '真实姓名',
  `phone_number` varchar(50) DEFAULT NULL COMMENT '用户手机号',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '新增时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '最后登陆时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '用户状态，0 关闭，1 开启',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='后台系统用户配置表';

INSERT INTO `system_user` (`id`, `user_account`, `password`, `name`, `phone_number`, `create_time`, `update_time`, `status`)
VALUES
  (1, 'admin', 'E10ADC3949BA59ABBE56E057F20F883E', '超级管理员', '888888888', NULL, NULL, 1);


drop table if exists operate_push_msg_log;
CREATE TABLE `operate_push_msg_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `operator` varchar(50) DEFAULT NULL COMMENT '操作人',
  `title` varchar(100) DEFAULT NULL COMMENT '运营消息标题',
  `content` varchar(500) DEFAULT NULL COMMENT '运营消息内容',
  `url` varchar(200) DEFAULT NULL COMMENT '运营消息对应的url',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `duration` int(11) DEFAULT NULL COMMENT '发送持续时间',
  `plan_sent_num` int(11) DEFAULT NULL COMMENT '计划发送数量',
  `actual_sent_num` int(11) DEFAULT NULL COMMENT '实际发送数量',
  `status` int(11) DEFAULT '1' COMMENT '//发送状态 1 、发送中，2、发送完成',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台系统运营人员推送消息操作日志记录表';


ALTER TABLE audio_approve ADD UNIQUE (audio_id)


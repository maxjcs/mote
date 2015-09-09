/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2015/4/24 17:21:40                           */
/*==============================================================*/

ALTER TABLE `teacher` MODIFY COLUMN `name`  varchar(50) NULL DEFAULT NULL AFTER `id`;
ALTER TABLE `teacher` MODIFY COLUMN `phone_number`  varchar(20) NULL DEFAULT NULL AFTER `name`;
ALTER TABLE `teacher` MODIFY COLUMN `id_number`  varchar(20) NULL DEFAULT NULL AFTER `password`;
ALTER TABLE `teacher` MODIFY COLUMN `qq`  varchar(20) NULL DEFAULT NULL AFTER `id_number`;
ALTER TABLE `teacher` MODIFY COLUMN `school_id`  bigint(20) NULL DEFAULT NULL AFTER `qq`;
ALTER TABLE `teacher` MODIFY COLUMN `id_card_image_url`  varchar(200) NULL DEFAULT NULL AFTER `school_id`;
ALTER TABLE `teacher` MODIFY COLUMN `student_card_image_url`  varchar(200) NULL DEFAULT NULL AFTER `id_card_image_url`;
ALTER TABLE `teacher` MODIFY COLUMN `status`  tinyint(4) NULL DEFAULT NULL AFTER `student_card_image_url`;
#增加在线状态
alter table teacher add column online_status varchar(1) ;
ALTER TABLE `teacher` ADD COLUMN `cur_org_id`  int(11) NULL DEFAULT NULL AFTER `online_status`;
ALTER TABLE `teacher` ADD COLUMN `capacity_test_status`  tinyint(4) NULL DEFAULT 0 COMMENT '0 未处理 1：已处理' AFTER `cur_org_id`;
ALTER TABLE `teacher` ADD COLUMN `capacity_test_complete_time`  datetime NULL DEFAULT NULL COMMENT '完成能力测试时间' AFTER `capacity_test_status`;
ALTER TABLE `teacher` ADD COLUMN `capacity_test_give_point`  int(11) NULL DEFAULT 0 COMMENT '能力测试赠送积分' AFTER `capacity_test_complete_time`;
ALTER TABLE `teacher` ADD COLUMN `capacity_test_is_joined`  int(11) NULL DEFAULT 0 COMMENT '是否参加新手任务1不参加 0参加' AFTER `capacity_test_give_point`;

alter table teacher modify column self_description text;

ALTER TABLE teacher ADD avatar_url varchar(200)  COMMENT '头像url';
ALTER TABLE teacher ADD gender int  COMMENT '性别 1：男 2：女';
ALTER TABLE teacher ADD star int  COMMENT '星级';
ALTER TABLE teacher ADD teacher_identify int  COMMENT '教师身份 1:教师 2:学生';
ALTER TABLE teacher ADD point int  COMMENT '积分';
ALTER TABLE teacher ADD qrcode_url varchar(200)  COMMENT '二维码url';
ALTER TABLE teacher ADD device_id varchar(100)  COMMENT '设备id';


#oldlu 20150413 去掉老表的必填验证
alter table audio_approve modify star tinyint;

alter table teacher modify name varchar(50);
alter table teacher modify id_number varchar(20);
alter table teacher modify qq varchar(20);
alter table teacher modify school_id bigint;
alter table teacher modify id_card_image_url varchar(200);
alter table teacher modify student_card_image_url varchar(200);

alter table teacher modify status tinyint(4);
alter table teacher modify province_id int;
alter table teacher modify city_id int;
alter table teacher modify weixin varchar(20);
alter table teacher modify subjects varchar(20);
alter table teacher modify grades varchar(20);

#oldlu 20150407 增加最后一次登录设备号、设备类型
alter table teacher add column last_device_id varchar(100);
alter table teacher add column last_device_type varchar(100);

#oldlu 20150327 增加最后做任务时间
alter table teacher add column last_task_time  datetime;
#oldlu 20150402 增加是否完成新手任务标记
alter table teacher add column complete_new_user_task  tinyint default 0;
		

alter table teacher modify name varchar(50) null;
alter table teacher modify id_number varchar(20) null;
alter table teacher modify qq varchar(20) null;
alter table teacher modify school_id bigint(20) null;
alter table teacher modify id_card_image_url varchar(200) null;
alter table teacher modify student_card_image_url varchar(200) null;
alter table teacher modify status tinyint(4) null;
alter table teacher modify province_id int(11) null;
alter table teacher modify city_id int(11) null;
alter table teacher modify weixin varchar(20) null;
alter table teacher modify subjects varchar(20) null;
alter table teacher modify grades varchar(20) null;
alter table teacher modify operater varchar(50) null;
alter table teacher modify bank_card varchar(50) null;
alter table teacher modify bank varchar(100) null;
alter table teacher modify alipay varchar(100) null;
alter table teacher modify course_time varchar(100) null;
alter table teacher modify self_description text null;
alter table teacher modify course_year int(50) null;
alter table teacher modify course_area varchar(100) null;
alter table teacher modify mingshihui tinyint(4) null;
alter table teacher modify xingjihua tinyint(4) null;
alter table teacher modify xingjihua tinyint(4) null;
alter table teacher modify avatar_url varchar(200) null;
alter table teacher modify gender int(11) null;
alter table teacher modify star int(11) null;
alter table teacher modify teacher_identify int(11) null;
alter table teacher modify point int(11) null;
alter table teacher modify qrcode_url int(11) null;
alter table teacher modify device_id varchar(100) null;
alter table teacher modify device_id varchar(100) null;
alter table teacher modify last_task_time datetime null;
alter table teacher modify last_device_id varchar(100) null;
alter table teacher modify last_device_type varchar(100) null;
alter table teacher modify complete_new_user_task tinyint(4) null;
alter table teacher modify online_status varchar(1) null;
alter table teacher modify cur_org_id int(11) null;
alter table teacher modify capacity_test_status tinyint(4) null;
alter table teacher modify capacity_test_complete_time datetime null;
alter table teacher modify capacity_test_give_point int(11) null;
alter table teacher modify capacity_test_is_joined int(11) null;


drop table if exists app_versions_info;

drop table if exists audio_student_evaluation;

drop table if exists batch_course_img;

drop table if exists common_config;

drop table if exists course_upload_batch;

drop table if exists course_ware;

drop table if exists course_ware_evaluation;

drop table if exists course_ware_grade;

drop table if exists course_ware_subject;

drop table if exists dictionary;

drop table if exists evaluation;

drop table if exists fee_log;

drop table if exists feedback;

drop table if exists feud_answer_detail;

drop table if exists feud_detail_wb;

drop table if exists feud_point_fee_conf;

drop table if exists feud_quest;

drop table if exists grade_subject;

drop table if exists income_log;

drop table if exists learn_message;

drop table if exists operate_push_msg_log;

drop table if exists orc_ops_sources;

drop table if exists org_quest;

drop table if exists organization_sources;

drop table if exists point_log;

drop table if exists point_offline;

drop table if exists push_msg_persistent;

drop table if exists rating;

drop table if exists rating_income_config;

drop table if exists school;

drop table if exists subject;

drop table if exists teacher_followed;

drop table if exists teacher_function;

drop table if exists teacher_grade;

drop table if exists teacher_subject;

drop table if exists vip_teacher;

drop table if exists vip_teacher_time;

drop table if exists verify_codes;



/*==============================================================*/
/* Table: app_versions_info                                     */
/*==============================================================*/
create table app_versions_info
(
   id                   int(11) not null auto_increment,
   version_code         int(11) comment '版本code int值',
   version_name         varchar(128) comment '版本name string',
   need_update          char(1) comment '是否需要升级，Y：需要，N：不需要',
   force_update         char(1) comment '是否需要强制升级，Y：需要强制升级 N：不需要强制升级',
   description          varchar(2048) comment '版本描述信息',
   app_type             int(11) comment '版本对应的app类型，1:安卓，2:IOS',
   add_time             timestamp comment '该版本新增时间',
   download_url         varchar(1024) comment '下载版本的url路径',
   primary key (id)
)DEFAULT CHARSET=utf8;

alter table app_versions_info comment 'app版本信息';

insert app_versions_info (id, version_code, version_name, need_update, force_update, description, app_type, add_time, download_url)
values(4, 1, '1.0', 'Y', 'Y', '安卓版本1.0', 1, '2015-04-11 14:40:06', 'http://www.baidu.com');

/*==============================================================*/
/* Table: audio_student_evaluation                              */
/*==============================================================*/
create table audio_student_evaluation
(
   id                   bigint(20) not null auto_increment comment '主键',
   audio_id             varchar(50) comment '音频ID',
   user_id              varchar(50) comment '用户ID',
   star                 int comment '评价级别',
   content              varchar(400) comment '评价内容',
   create_time          timestamp comment '评价时间',
   primary key (id)
)DEFAULT CHARSET=utf8;

alter table audio_student_evaluation comment '音频评价';

/*==============================================================*/
/* Table: batch_course_img                                      */
/*==============================================================*/
create table batch_course_img
(
   id                   bigint not null auto_increment,
   img_url              varchar(100) comment '图片url',
   question_real_id     bigint comment '识别后对应的问题Id',
   create_time          datetime comment '创建时间',
   update_time          timestamp comment '更新时间',
   teacher_id           varchar(50) comment '名师汇老师id',
   course_ware_id       bigint comment '课件id',
   batch_id             varchar(50) comment '批次id',
   ocr_status           int comment 'ocr 状态 1未识别 2 识别完成 3识别失败',
   audio_whiteboard_id  varchar(50) comment '音频白版id',
   primary key (id)
)DEFAULT CHARSET=utf8;

alter table batch_course_img comment '课件批量上传图片表';

/*==============================================================*/
/* Table: common_config                                         */
/*==============================================================*/
CREATE TABLE `common_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `config_key` varchar(256) NOT NULL DEFAULT '' COMMENT 'key值，该字段唯一',
  `config_value` int(11) NOT NULL COMMENT 'key对应的数值',
  `description` varchar(512) DEFAULT NULL COMMENT '该key－value的描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='通用配置key_value表';


/*==============================================================*/
/* Table: course_upload_batch                                   */
/*==============================================================*/
create table course_upload_batch
(
   id                   bigint not null auto_increment,
   create_time          datetime comment '创建时间',
   update_time          timestamp comment '修改时间',
   batch_id             varchar(50) comment '上传批次',
   status               int comment '批次状态 1未完成，2已经完成',
   teacher_id           varchar(50) comment '教师id',
   primary key (id)
)DEFAULT CHARSET=utf8;

alter table course_upload_batch comment '上传批次表';

/*==============================================================*/
/* Table: course_ware                                           */
/*==============================================================*/
create table course_ware
(
   id                   bigint not null auto_increment,
   teacher_id           varchar(50) comment '名师汇tearcher Id',
   name                 varchar(100) comment '课件名',
   price                int comment '价格',
   introduce            varchar(2000) comment '介绍',
   create_time          datetime comment '创建时间',
   update_time          timestamp comment '修改时间',
   status               varchar(10) comment '状态 1位发布，2已发布',
   version              int comment '上线的版本',
   is_delete            char comment '是否删除',
   primary key (id)
)DEFAULT CHARSET=utf8;

alter table course_ware comment '课件表';

/*==============================================================*/
/* Table: course_ware_evaluation                                */
/*==============================================================*/
create table course_ware_evaluation
(
   id                   bigint not null auto_increment,
   course_ware_id       bigint comment '课件id',
   user_id              varchar(50) comment '用户id',
   content              varchar(1000) comment '评价内容',
   star                 tinyint comment '星级',
   create_time          datetime comment '创建时间',
   update_time          timestamp comment '更新时间',
   primary key (id)
)DEFAULT CHARSET=utf8;

alter table course_ware_evaluation comment '课件评价';

/*==============================================================*/
/* Table: course_ware_grade                                     */
/*==============================================================*/
create table course_ware_grade
(
   id                   bigint not null auto_increment,
   course_ware_id       bigint comment '课件id',
   grade_id             bigint comment '年级id',
   teacher_id           varchar(50) comment '老师id',
   create_time          datetime,
   update_time          timestamp,
   primary key (id)
)DEFAULT CHARSET=utf8;

alter table course_ware_grade comment '课件年级关联表';

/*==============================================================*/
/* Table: course_ware_subject                                   */
/*==============================================================*/
create table course_ware_subject
(
   id                   bigint not null auto_increment,
   course_ware_id       bigint comment '课件id',
   subject_id           bigint comment '学科id',
   teacher_id           varchar(50) comment '老师id',
   create_time          datetime,
   update_time          timestamp,
   primary key (id)
)DEFAULT CHARSET=utf8;

alter table course_ware_subject comment '课件学科表';

/*==============================================================*/
/* Table: dictionary                                            */
/*==============================================================*/
create table dictionary
(
   id                   int not null auto_increment comment '主键',
   type                 int not null comment '字典类型 1：性别 2：教师身份 3：辅导时间',
   name                 varchar(64) comment '标题',
   value                varchar(32) comment '值',
   create_time          timestamp not null comment '创建时间',
   sort_no              int,
   primary key (id)
)DEFAULT CHARSET=utf8;

alter table dictionary comment '数据字段，保存通用数据';

/*==============================================================*/
/* Table: evaluation                                            */
/*==============================================================*/
create table evaluation
(
   id                   int not null auto_increment comment '主键',
   level                int comment '评价级别',
   name                 varchar(100) comment '好评',
   description          varchar(512) comment '描述',
   point                int comment '评价得积分',
   teacher_identify     int(11) comment '教师身份 1、教师；2、学生',
   primary key (id)
)DEFAULT CHARSET=utf8;

alter table evaluation comment '评价定义';

/*==============================================================*/
/* Table: fee_log                                               */
/*==============================================================*/
DROP TABLE IF EXISTS `fee_log`;
CREATE TABLE `fee_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `money` int(11) DEFAULT NULL COMMENT '费用',
  `teacher_id` varchar(50) DEFAULT NULL COMMENT '教师ID',
  `description` varchar(256) DEFAULT NULL COMMENT '费用变化描述',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=915 DEFAULT CHARSET=utf8 COMMENT='费用变化日志';

alter table fee_log comment '费用变化日志';

/*==============================================================*/
/* Table: feedback                                              */
/*==============================================================*/
create table feedback
(
   id                   bigint(20) not null auto_increment,
   teacher_id           varchar(50) not null,
   content              varchar(512),
   phone_number         varchar(20),
   create_time          timestamp not null,
   primary key (id)
)DEFAULT CHARSET=utf8;

alter table feedback comment '意见反馈';

/*==============================================================*/
/* Table: feud_answer_detail                                    */
/*==============================================================*/
create table feud_answer_detail
(
   id                   bigint(20) not null auto_increment,
   feud_quest_id        bigint(20) comment '抢答ID',
   teacher_id           varchar(50) comment '抢答teacher_id',
   feud_type            bigint(20) comment '1,audio,2 whiteboard',
   audio_whiteboard_id  varchar(50) comment 'audio_upload 或者 whiteboard id  对应 audio_whiteboard_upload id',
   audio_whiteboard_url varchar(50) comment '音频白板url',
   create_time          datetime,
   update_time          timestamp,
   status               int comment '抢答状态 1待提交，2已提交，3已失效',
   quest_real_id        bigint(20) comment '问题ID',
   evaluate             int(5) comment '评价,1 好评,2 中评,3 差评',
   content              varchar(100) comment '评价内容',
   point                int(5) comment '积分：冗余字段  默认好评对应的积分 差评后需要更新该值',
   fee                  bigint(20) comment '抢答费：单位 分。冗余字段',
   primary key (id)
)DEFAULT CHARSET=utf8;

alter table feud_answer_detail comment '教师抢答明细表


';

/*==============================================================*/
/* Table: feud_detail_wb                                        */
/*==============================================================*/
create table feud_detail_wb
(
   id                   bigint not null auto_increment,
   feud_detail_id       bigint,
   file_url             varchar(50) comment '白板文件下载地址:一个白板对应多个文件下载地址',
   wb_id                varchar(50) comment '白板ID',
   file_type            varchar(50) comment '文件类型',
   create_time          datetime,
   update_time          timestamp,
   primary key (id)
)DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: feud_quest                                            */
/*==============================================================*/
create table feud_quest
(
   id                   bigint(20) not null auto_increment,
   question_real_id     bigint(20) comment '题目ID',
   student_id           varchar(50) comment '请求问题的学生用户ID',
   image_id             varchar(50) comment '题目图片ID',
   status               bigint(20) comment '状态:1 待抢答,2 抢答中,3 已抢答,4 已过期',
   feud_answer_teacher_id varchar(50) comment '首次抢答教师ID',
   feud_answer_detail_id bigint(20) comment '首次抢答明细ID',
   create_time          datetime,
   update_time          timestamp,
   primary key (id)
)DEFAULT CHARSET=utf8;

alter table feud_quest comment '问题抢答';

/*==============================================================*/
/* Table: grade_subject                                         */
/*==============================================================*/
create table grade_subject
(
   id                   int not null auto_increment,
   grade_id             int not null comment '年级id',
   subject_id           int not null comment '学科id',
   create_time          datetime,
   update_time          timestamp,
   primary key (id)
)DEFAULT CHARSET=utf8;

alter table grade_subject comment '年级与学科对应';

/*==============================================================*/
/* Table: income_log                                            */
/*==============================================================*/
create table income_log
(
   id                   bigint not null auto_increment,
   income               int comment '积分',
   teacher_id           varchar(50) comment '教师ID',
   description          varchar(256),
   create_time          datetime,
   update_time          timestamp,
   primary key (id)
)DEFAULT CHARSET=utf8;

alter table income_log comment '积分变化';

/*==============================================================*/
/* Table: learn_message                                         */
/*==============================================================*/
DROP TABLE IF EXISTS `learn_message`;
CREATE TABLE `learn_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `teacher_id` varchar(50) DEFAULT NULL,
  `student_id` varchar(50) DEFAULT NULL,
  `send_msg_user_type` int(5) DEFAULT NULL COMMENT '消息发送者 1 teacher,2 student',
  `msg_type` int(5) DEFAULT NULL COMMENT '消息类型 1 txt ,2 img',
  `teacher_offer_id` varchar(50) DEFAULT NULL COMMENT '课件 音频 白版',
  `content` varchar(255) DEFAULT NULL COMMENT '如果消息类型是图片,图片的URL,如果是文字则是文本内容',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `teacher_offer_type` int(11) DEFAULT NULL COMMENT '1 音频 2 白板 3课件',
  `is_read` varchar(5) DEFAULT NULL COMMENT '是否阅读',
  `is_student_read` varchar(5) DEFAULT NULL COMMENT '学生是否阅读 表示位',
  `teacher_offer_desc` varchar(255) DEFAULT NULL,
  `image_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4577 DEFAULT CHARSET=utf8 COMMENT='互动学习消息';

alter table learn_message comment '互动学习消息';

/*==============================================================*/
/* Table: operate_push_msg_log                                  */
/*==============================================================*/
create table operate_push_msg_log
(
   id                   int not null auto_increment,
   operator             varchar(50) comment '操作人',
   title                varchar(100) comment '运营消息标题',
   content              varchar(500) comment '运营消息内容',
   url                  varchar(200) comment '运营消息对应的url',
   create_time          datetime,
   duration             int comment '发送持续时间',
   plan_sent_num        int comment '计划发送数量',
   actual_sent_num      int comment '实际发送数量',
   primary key (id)
)DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: org_quest                                             */
/*==============================================================*/
create table org_quest
(
   org_id               int not null auto_increment comment '机构id',
   real_quest_id        bigint comment '题库真实ID',
   create_time          datetime comment '创建时间',
   update_time          datetime,
   primary key (org_id)
)DEFAULT CHARSET=utf8;

alter table org_quest comment '机构与题库关系';

/*==============================================================*/
/* Table: point_log                                             */
/*==============================================================*/
create table point_log
(
   id                   bigint not null auto_increment comment '主键',
   point                int comment '积分',
   teacher_id           varchar(50) comment '教师ID',
   description          varchar(256) comment '积分变化描述',
   create_time          datetime,
   update_time          timestamp,
   primary key (id)
)DEFAULT CHARSET=utf8;

alter table point_log comment '积分变化';

/*==============================================================*/
/* Table: point_offline                                         */
/*==============================================================*/
create table point_offline
(
   id                   int not null auto_increment comment '主键',
   name                 varchar(100) comment '标题',
   description          varchar(512) comment '描述',
   money                int comment '线下收入',
   point                int comment '线下收入抵积分',
   primary key (id)
)DEFAULT CHARSET=utf8;

alter table point_offline comment '离线积分';

/*==============================================================*/
/* Table: push_msg_persistent                                   */
/*==============================================================*/

CREATE TABLE `push_msg_persistent` (
  `id` varchar(50) NOT NULL DEFAULT '' COMMENT '主键UUID',
  `content` varchar(2048) DEFAULT '' COMMENT '消息内容',
  `msg_type` int(11) DEFAULT NULL COMMENT '消息类型',
  `is_send` varchar(11) DEFAULT NULL COMMENT '推送消息是否已经成功发出给用户：Y:是，N:否',
  `is_read` varchar(11) DEFAULT NULL COMMENT '消息标记位置 已读：Y 未读：N',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '消息发送时间',
  `user_id` varchar(100) DEFAULT NULL COMMENT '接受消息的用户id',
  `phone_type` varchar(50) DEFAULT NULL COMMENT '接收手机的类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息推送持久化';

/*==============================================================*/
/* Table: rating                                                */
/*==============================================================*/
create table rating
(
   id                   tinyint(4) not null auto_increment comment '主键',
   star                 tinyint(4) not null comment '星级 1,2,3,4,5',
   name                 varchar(100),
   description          varchar(200) comment '描述',
   points               int comment '星级对应积分',
   primary key (id)
)DEFAULT CHARSET=utf8;

alter table rating comment '星级特权';

/*==============================================================*/
/* Table: rating_income_config                                  */
/*==============================================================*/
create table rating_income_config
(
   id                   int(11) not null auto_increment,
   star                 tinyint comment '星级',
   teacher_identify     int(11) comment '教师身份 1、教师；2、学生',
   fee                  int comment '费用，单位是分',
   description          varchar(256),
   primary key (id)
)DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: teacher_followed                                      */
/*==============================================================*/
create table teacher_followed
(
   id                   bigint not null auto_increment,
   teacher_id           varchar(50) comment '教师Id',
   user_id              varchar(50) comment '学生Id',
   create_time          datetime,
   update_time          timestamp,
   primary key (id)
)DEFAULT CHARSET=utf8;

alter table teacher_followed comment '学生关注老师表';

/*==============================================================*/
/* Table: teacher_function                                      */
/*==============================================================*/
create table teacher_function
(
   id                   int not null auto_increment comment 'teacherId teacher表主键id',
   everyday_task        tinyint(4) comment '每日任务功能模块，1:可用，0:不可用',
   feud                 tinyint(4) comment '抢答功能模块，1:可用，0:不可用',
   learn_talk           tinyint(4) comment '互动学习功能模块，1:可用，0:不可用',
   pay                  tinyint(4) comment '支付模块，1:可用，0:不可用',
   primary key (id)
)DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: teacher_grade                                         */
/*==============================================================*/
create table teacher_grade
(
   id                   bigint not null auto_increment,
   grade_id             bigint comment '年级id',
   teacher_id           varchar(50) comment '老师id',
   create_time          datetime,
   update_time          timestamp,
   primary key (id)
)DEFAULT CHARSET=utf8;

alter table teacher_grade comment '教师年级关联表';

/*==============================================================*/
/* Table: teacher_subject                                       */
/*==============================================================*/
create table teacher_subject
(
   id                   bigint not null auto_increment,
   subject_id           bigint comment '学科id',
   teacher_id           varchar(50) comment '老师id',
   create_time          datetime,
   update_time          timestamp,
   primary key (id)
)DEFAULT CHARSET=utf8;

alter table teacher_subject comment '教师学科表';

/*==============================================================*/
/* Table: vip_teacher                                           */
/*==============================================================*/
create table vip_teacher
(
   id                   bigint not null auto_increment,
   teacher_id           varchar(50) comment '教师Id',
   apply_status         tinyint(4) comment '申请状态()',
   city_id              int comment '城市id',
   create_time          datetime comment '创建时间',
   update_time          timestamp comment '修改时间',
   primary key (id)
)DEFAULT CHARSET=utf8;

alter table vip_teacher comment '名师汇老师';

/*==============================================================*/
/* Table: vip_teacher_time                                      */
/*==============================================================*/
create table vip_teacher_time
(
   id                   bigint not null auto_increment,
   teacher_id           varchar(50) comment '老师id',
   time_id              bigint comment '授课时间id（来自dict）',
   create_time          datetime,
   update_time          timestamp,
   primary key (id)
)DEFAULT CHARSET=utf8;

alter table vip_teacher_time comment '名师汇老师授课时间表';

/**--------------------------------alert-------------------------------------------------**/

alter table question add column source varchar(1) comment '1：每日热题 2：抢答题 3：机构题';

/**------------------------------------蒋昌盛-----------------------------------------------**/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for city
-- ----------------------------
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) NOT NULL COMMENT '父id',
  `name` varchar(50) DEFAULT NULL COMMENT '城市名称',
  `sort_number` bigint(20) NOT NULL COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_city_name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8 COMMENT='城市表';

-- ----------------------------
-- Records of city
-- ----------------------------
INSERT INTO `city` VALUES ('1', '0', '安徽', '1', '2015-04-20 03:40:37', '2015-04-20 03:40:37');
INSERT INTO `city` VALUES ('2', '1', '合肥', '1', '2015-04-20 03:40:37', '2015-04-20 03:40:37');
INSERT INTO `city` VALUES ('3', '0', '北京', '2', '2015-04-20 03:40:37', '2015-04-20 03:40:37');
INSERT INTO `city` VALUES ('4', '3', '北京市', '1', '2015-04-20 03:40:37', '2015-04-20 03:40:37');
INSERT INTO `city` VALUES ('5', '0', '重庆', '3', '2015-04-20 03:40:37', '2015-04-20 03:40:37');
INSERT INTO `city` VALUES ('6', '5', '重庆市', '1', '2015-04-20 03:40:37', '2015-04-20 03:40:37');
INSERT INTO `city` VALUES ('7', '0', '广东', '4', '2015-04-20 03:40:37', '2015-04-20 03:40:37');
INSERT INTO `city` VALUES ('8', '7', '广州', '1', '2015-04-20 03:40:37', '2015-04-20 03:40:37');
INSERT INTO `city` VALUES ('9', '0', '河北', '5', '2015-04-20 03:40:38', '2015-04-20 03:40:38');
INSERT INTO `city` VALUES ('10', '9', '邯郸', '1', '2015-04-20 03:40:38', '2015-04-20 03:40:38');
INSERT INTO `city` VALUES ('11', '0', '河南', '6', '2015-04-20 03:40:38', '2015-04-20 03:40:38');
INSERT INTO `city` VALUES ('12', '11', '郑州', '1', '2015-04-20 03:40:38', '2015-04-20 03:40:38');
INSERT INTO `city` VALUES ('13', '0', '黑龙江', '7', '2015-04-20 03:40:38', '2015-04-20 03:40:38');
INSERT INTO `city` VALUES ('14', '13', '哈尔滨', '1', '2015-04-20 03:40:38', '2015-04-20 03:40:38');
INSERT INTO `city` VALUES ('15', '0', '湖北', '8', '2015-04-20 03:40:38', '2015-04-20 03:40:38');
INSERT INTO `city` VALUES ('16', '15', '武汉', '1', '2015-04-20 03:40:38', '2015-04-20 03:40:38');
INSERT INTO `city` VALUES ('17', '0', '湖南', '9', '2015-04-20 03:40:38', '2015-04-20 03:40:38');
INSERT INTO `city` VALUES ('18', '17', '长沙', '1', '2015-04-20 03:40:38', '2015-04-20 03:40:38');
INSERT INTO `city` VALUES ('19', '0', '吉林', '10', '2015-04-20 03:40:38', '2015-04-20 03:40:38');
INSERT INTO `city` VALUES ('20', '19', '长春', '1', '2015-04-20 03:40:38', '2015-04-20 03:40:38');
INSERT INTO `city` VALUES ('21', '0', '江苏', '11', '2015-04-20 03:40:38', '2015-04-20 03:40:38');
INSERT INTO `city` VALUES ('22', '21', '南京', '1', '2015-04-20 03:40:38', '2015-04-20 03:40:38');
INSERT INTO `city` VALUES ('23', '0', '辽宁', '12', '2015-04-20 03:40:38', '2015-04-20 03:40:38');
INSERT INTO `city` VALUES ('24', '23', '大连', '1', '2015-04-20 03:40:38', '2015-04-20 03:40:38');
INSERT INTO `city` VALUES ('25', '23', '沈阳', '2', '2015-04-20 03:40:38', '2015-04-20 03:40:38');
INSERT INTO `city` VALUES ('26', '0', '内蒙古', '13', '2015-04-20 03:40:38', '2015-04-20 03:40:38');
INSERT INTO `city` VALUES ('27', '26', '呼和浩特', '1', '2015-04-20 03:40:38', '2015-04-20 03:40:38');
INSERT INTO `city` VALUES ('28', '0', '山东', '14', '2015-04-20 03:40:38', '2015-04-20 03:40:38');
INSERT INTO `city` VALUES ('29', '28', '威海', '1', '2015-04-20 03:40:38', '2015-04-20 03:40:38');
INSERT INTO `city` VALUES ('30', '28', '淄博', '2', '2015-04-20 03:40:38', '2015-04-20 03:40:38');
INSERT INTO `city` VALUES ('31', '28', '烟台', '3', '2015-04-20 03:40:39', '2015-04-20 03:40:39');
INSERT INTO `city` VALUES ('32', '28', '青岛', '4', '2015-04-20 03:40:39', '2015-04-20 03:40:39');
INSERT INTO `city` VALUES ('33', '0', '山西', '15', '2015-04-20 03:40:39', '2015-04-20 03:40:39');
INSERT INTO `city` VALUES ('34', '33', '太原', '1', '2015-04-20 03:40:39', '2015-04-20 03:40:39');
INSERT INTO `city` VALUES ('35', '0', '陕西', '16', '2015-04-20 03:40:39', '2015-04-20 03:40:39');
INSERT INTO `city` VALUES ('36', '35', '西安', '1', '2015-04-20 03:40:39', '2015-04-20 03:40:39');
INSERT INTO `city` VALUES ('37', '0', '上海', '17', '2015-04-20 03:40:39', '2015-04-20 03:40:39');
INSERT INTO `city` VALUES ('38', '37', '上海市', '1', '2015-04-20 03:40:39', '2015-04-20 03:40:39');
INSERT INTO `city` VALUES ('39', '0', '四川', '18', '2015-04-20 03:40:39', '2015-04-20 03:40:39');
INSERT INTO `city` VALUES ('40', '39', '成都', '1', '2015-04-20 03:40:39', '2015-04-20 03:40:39');
INSERT INTO `city` VALUES ('41', '0', '天津', '19', '2015-04-20 03:40:39', '2015-04-20 03:40:39');
INSERT INTO `city` VALUES ('42', '41', '天津市', '1', '2015-04-20 03:40:39', '2015-04-20 03:40:39');
INSERT INTO `city` VALUES ('43', '0', '福建', '20', '2015-04-20 03:40:39', '2015-04-20 03:40:39');
INSERT INTO `city` VALUES ('44', '0', '甘肃', '21', '2015-04-20 03:40:39', '2015-04-20 03:40:39');
INSERT INTO `city` VALUES ('45', '0', '广西', '22', '2015-04-20 03:40:39', '2015-04-20 03:40:39');
INSERT INTO `city` VALUES ('46', '0', '贵州', '23', '2015-04-20 03:40:39', '2015-04-20 03:40:39');
INSERT INTO `city` VALUES ('47', '0', '海南', '24', '2015-04-20 03:40:39', '2015-04-20 03:40:39');
INSERT INTO `city` VALUES ('48', '0', '江西', '25', '2015-04-20 03:40:39', '2015-04-20 03:40:39');
INSERT INTO `city` VALUES ('49', '0', '宁夏', '26', '2015-04-20 03:40:39', '2015-04-20 03:40:39');
INSERT INTO `city` VALUES ('50', '0', '青海', '27', '2015-04-20 03:40:39', '2015-04-20 03:40:39');
INSERT INTO `city` VALUES ('51', '0', '香港', '28', '2015-04-20 03:40:39', '2015-04-20 03:40:39');
INSERT INTO `city` VALUES ('52', '0', '新疆', '29', '2015-04-20 03:40:39', '2015-04-20 03:40:39');
INSERT INTO `city` VALUES ('53', '0', '云南', '30', '2015-04-20 03:40:39', '2015-04-20 03:40:39');
INSERT INTO `city` VALUES ('54', '0', '浙江', '31', '2015-04-20 03:40:39', '2015-04-20 03:40:39');


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

DROP TABLE IF EXISTS `school`;
CREATE TABLE `school` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `city_id` int(10) NOT NULL COMMENT '城市id',
  `name` varchar(50) NOT NULL COMMENT '学校名称',
  `sort_number` bigint(20) NOT NULL COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_school_name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=183 DEFAULT CHARSET=utf8 COMMENT='学校表';

-- ----------------------------
-- Records of school
-- ----------------------------
INSERT INTO `school` VALUES ('1', '1', '安徽师范大学', '1', '2015-04-20 03:44:03', '2015-04-20 03:44:03');
INSERT INTO `school` VALUES ('2', '1', '阜阳师范学院', '2', '2015-04-20 03:44:03', '2015-04-20 03:44:03');
INSERT INTO `school` VALUES ('3', '1', '安庆师范学院', '3', '2015-04-20 03:44:04', '2015-04-20 03:44:04');
INSERT INTO `school` VALUES ('4', '1', '淮北师范大学', '4', '2015-04-20 03:44:04', '2015-04-20 03:44:04');
INSERT INTO `school` VALUES ('5', '1', '黄山学院', '5', '2015-04-20 03:44:04', '2015-04-20 03:44:04');
INSERT INTO `school` VALUES ('6', '1', '皖西学院', '6', '2015-04-20 03:44:04', '2015-04-20 03:44:04');
INSERT INTO `school` VALUES ('7', '1', '滁州学院', '7', '2015-04-20 03:44:04', '2015-04-20 03:44:04');
INSERT INTO `school` VALUES ('8', '1', '宿州学院', '8', '2015-04-20 03:44:04', '2015-04-20 03:44:04');
INSERT INTO `school` VALUES ('9', '1', '巢湖学院', '9', '2015-04-20 03:44:04', '2015-04-20 03:44:04');
INSERT INTO `school` VALUES ('10', '1', '淮南师范学院', '10', '2015-04-20 03:44:04', '2015-04-20 03:44:04');
INSERT INTO `school` VALUES ('11', '1', '安徽科技学院', '11', '2015-04-20 03:44:04', '2015-04-20 03:44:04');
INSERT INTO `school` VALUES ('12', '1', '安徽师范大学皖江学院', '12', '2015-04-20 03:44:04', '2015-04-20 03:44:04');
INSERT INTO `school` VALUES ('13', '1', '淮北师范大学信息学院', '13', '2015-04-20 03:44:04', '2015-04-20 03:44:04');
INSERT INTO `school` VALUES ('14', '1', '合肥师范学院', '14', '2015-04-20 03:44:04', '2015-04-20 03:44:04');
INSERT INTO `school` VALUES ('15', '3', '首都师范大学', '15', '2015-04-20 03:44:05', '2015-04-20 03:44:05');
INSERT INTO `school` VALUES ('16', '3', '北京大学', '16', '2015-04-20 03:44:05', '2015-04-20 03:44:05');
INSERT INTO `school` VALUES ('17', '3', '北京师范大学', '17', '2015-04-20 03:44:05', '2015-04-20 03:44:05');
INSERT INTO `school` VALUES ('18', '5', '重庆师范大学', '18', '2015-04-20 03:44:05', '2015-04-20 03:44:05');
INSERT INTO `school` VALUES ('19', '5', '长江师范学院', '19', '2015-04-20 03:44:05', '2015-04-20 03:44:05');
INSERT INTO `school` VALUES ('20', '43', '武夷学院', '20', '2015-04-20 03:44:05', '2015-04-20 03:44:05');
INSERT INTO `school` VALUES ('21', '43', '福建师范大学', '21', '2015-04-20 03:44:05', '2015-04-20 03:44:05');
INSERT INTO `school` VALUES ('22', '43', '宁德师范学院', '22', '2015-04-20 03:44:05', '2015-04-20 03:44:05');
INSERT INTO `school` VALUES ('23', '43', '泉州师范学院', '23', '2015-04-20 03:44:05', '2015-04-20 03:44:05');
INSERT INTO `school` VALUES ('24', '43', '漳州师范学院', '24', '2015-04-20 03:44:05', '2015-04-20 03:44:05');
INSERT INTO `school` VALUES ('25', '44', '西北师范大学', '25', '2015-04-20 03:44:05', '2015-04-20 03:44:05');
INSERT INTO `school` VALUES ('26', '44', '陇东学院', '26', '2015-04-20 03:44:05', '2015-04-20 03:44:05');
INSERT INTO `school` VALUES ('27', '44', '甘肃民族师范学院', '27', '2015-04-20 03:44:05', '2015-04-20 03:44:05');
INSERT INTO `school` VALUES ('28', '44', '西北师范大学知行学院', '28', '2015-04-20 03:44:05', '2015-04-20 03:44:05');
INSERT INTO `school` VALUES ('29', '44', '天水师范学院', '29', '2015-04-20 03:44:05', '2015-04-20 03:44:05');
INSERT INTO `school` VALUES ('30', '7', '华南师范大学', '30', '2015-04-20 03:44:05', '2015-04-20 03:44:05');
INSERT INTO `school` VALUES ('31', '7', '韩山师范学院', '31', '2015-04-20 03:44:05', '2015-04-20 03:44:05');
INSERT INTO `school` VALUES ('32', '7', '湛江师范学院', '32', '2015-04-20 03:44:05', '2015-04-20 03:44:05');
INSERT INTO `school` VALUES ('33', '7', '广东技术师范学院', '33', '2015-04-20 03:44:05', '2015-04-20 03:44:05');
INSERT INTO `school` VALUES ('34', '7', '广东第二师范学院', '34', '2015-04-20 03:44:06', '2015-04-20 03:44:06');
INSERT INTO `school` VALUES ('35', '45', '广西师范大学', '35', '2015-04-20 03:44:06', '2015-04-20 03:44:06');
INSERT INTO `school` VALUES ('36', '45', '广西师范学院', '36', '2015-04-20 03:44:06', '2015-04-20 03:44:06');
INSERT INTO `school` VALUES ('37', '45', '广西民族师范学院', '37', '2015-04-20 03:44:06', '2015-04-20 03:44:06');
INSERT INTO `school` VALUES ('38', '45', '河池学院', '38', '2015-04-20 03:44:06', '2015-04-20 03:44:06');
INSERT INTO `school` VALUES ('39', '45', '玉林师范学院', '39', '2015-04-20 03:44:06', '2015-04-20 03:44:06');
INSERT INTO `school` VALUES ('40', '46', '贵州师范大学', '40', '2015-04-20 03:44:06', '2015-04-20 03:44:06');
INSERT INTO `school` VALUES ('41', '46', '遵义师范学院', '41', '2015-04-20 03:44:06', '2015-04-20 03:44:06');
INSERT INTO `school` VALUES ('42', '46', '兴义民族师范学院', '42', '2015-04-20 03:44:06', '2015-04-20 03:44:06');
INSERT INTO `school` VALUES ('43', '46', '毕节学院', '43', '2015-04-20 03:44:06', '2015-04-20 03:44:06');
INSERT INTO `school` VALUES ('44', '46', '黔南民族师范学院', '44', '2015-04-20 03:44:06', '2015-04-20 03:44:06');
INSERT INTO `school` VALUES ('45', '46', '六盘水师范学院', '45', '2015-04-20 03:44:06', '2015-04-20 03:44:06');
INSERT INTO `school` VALUES ('46', '46', '贵州师范大学求是学院', '46', '2015-04-20 03:44:06', '2015-04-20 03:44:06');
INSERT INTO `school` VALUES ('47', '46', '贵州师范学院', '47', '2015-04-20 03:44:06', '2015-04-20 03:44:06');
INSERT INTO `school` VALUES ('48', '47', '海南师范大学', '48', '2015-04-20 03:44:06', '2015-04-20 03:44:06');
INSERT INTO `school` VALUES ('49', '9', '河北轨道运输职业技术学院', '49', '2015-04-20 03:44:07', '2015-04-20 03:44:07');
INSERT INTO `school` VALUES ('50', '9', '河北工业大学', '50', '2015-04-20 03:44:07', '2015-04-20 03:44:07');
INSERT INTO `school` VALUES ('51', '9', '河北师范大学', '51', '2015-04-20 03:44:07', '2015-04-20 03:44:07');
INSERT INTO `school` VALUES ('52', '9', '河北民族师范学院', '52', '2015-04-20 03:44:07', '2015-04-20 03:44:07');
INSERT INTO `school` VALUES ('53', '9', '唐山师范学院', '53', '2015-04-20 03:44:07', '2015-04-20 03:44:07');
INSERT INTO `school` VALUES ('54', '9', '廊坊师范学院', '54', '2015-04-20 03:44:07', '2015-04-20 03:44:07');
INSERT INTO `school` VALUES ('55', '9', '衡水学院', '55', '2015-04-20 03:44:07', '2015-04-20 03:44:07');
INSERT INTO `school` VALUES ('56', '9', '石家庄学院', '56', '2015-04-20 03:44:07', '2015-04-20 03:44:07');
INSERT INTO `school` VALUES ('57', '9', '邯郸学院', '57', '2015-04-20 03:44:07', '2015-04-20 03:44:07');
INSERT INTO `school` VALUES ('58', '9', '邢台学院', '58', '2015-04-20 03:44:07', '2015-04-20 03:44:07');
INSERT INTO `school` VALUES ('59', '9', '沧州师范学院', '59', '2015-04-20 03:44:07', '2015-04-20 03:44:07');
INSERT INTO `school` VALUES ('60', '9', '河北科技师范学院', '60', '2015-04-20 03:44:07', '2015-04-20 03:44:07');
INSERT INTO `school` VALUES ('61', '9', '河北师范大学汇华学院', '61', '2015-04-20 03:44:07', '2015-04-20 03:44:07');
INSERT INTO `school` VALUES ('62', '11', '河南科技大学', '62', '2015-04-20 03:44:07', '2015-04-20 03:44:07');
INSERT INTO `school` VALUES ('63', '11', '河南师范大学', '63', '2015-04-20 03:44:07', '2015-04-20 03:44:07');
INSERT INTO `school` VALUES ('64', '11', '信阳师范学院', '64', '2015-04-20 03:44:07', '2015-04-20 03:44:07');
INSERT INTO `school` VALUES ('65', '11', '周口师范学院', '65', '2015-04-20 03:44:07', '2015-04-20 03:44:07');
INSERT INTO `school` VALUES ('66', '11', '安阳师范学院', '66', '2015-04-20 03:44:07', '2015-04-20 03:44:07');
INSERT INTO `school` VALUES ('67', '11', '南阳师范学院', '67', '2015-04-20 03:44:07', '2015-04-20 03:44:07');
INSERT INTO `school` VALUES ('68', '11', '洛阳师范学院', '68', '2015-04-20 03:44:08', '2015-04-20 03:44:08');
INSERT INTO `school` VALUES ('69', '11', '商丘师范学院', '69', '2015-04-20 03:44:08', '2015-04-20 03:44:08');
INSERT INTO `school` VALUES ('70', '11', '黄淮学院', '70', '2015-04-20 03:44:08', '2015-04-20 03:44:08');
INSERT INTO `school` VALUES ('71', '11', '平顶山学院', '71', '2015-04-20 03:44:08', '2015-04-20 03:44:08');
INSERT INTO `school` VALUES ('72', '11', '郑州师范学院', '72', '2015-04-20 03:44:08', '2015-04-20 03:44:08');
INSERT INTO `school` VALUES ('73', '11', '河南师范大学新联学院', '73', '2015-04-20 03:44:08', '2015-04-20 03:44:08');
INSERT INTO `school` VALUES ('74', '11', '信阳师范学院华锐学院', '74', '2015-04-20 03:44:08', '2015-04-20 03:44:08');
INSERT INTO `school` VALUES ('75', '11', '安阳师范学院人文管理学院', '75', '2015-04-20 03:44:08', '2015-04-20 03:44:08');
INSERT INTO `school` VALUES ('76', '13', '哈尔滨师范大学', '76', '2015-04-20 03:44:08', '2015-04-20 03:44:08');
INSERT INTO `school` VALUES ('77', '13', '牡丹江师范学院', '77', '2015-04-20 03:44:08', '2015-04-20 03:44:08');
INSERT INTO `school` VALUES ('78', '13', '黑龙江外国语学院', '78', '2015-04-20 03:44:08', '2015-04-20 03:44:08');
INSERT INTO `school` VALUES ('79', '15', '湖北师范学院', '79', '2015-04-20 03:44:08', '2015-04-20 03:44:08');
INSERT INTO `school` VALUES ('80', '15', '黄冈师范学院', '80', '2015-04-20 03:44:08', '2015-04-20 03:44:08');
INSERT INTO `school` VALUES ('81', '15', '湖北师范学院文理学院', '81', '2015-04-20 03:44:08', '2015-04-20 03:44:08');
INSERT INTO `school` VALUES ('82', '15', '湖北第二师范学院', '82', '2015-04-20 03:44:08', '2015-04-20 03:44:08');
INSERT INTO `school` VALUES ('83', '15', '华中师范大学', '83', '2015-04-20 03:44:08', '2015-04-20 03:44:08');
INSERT INTO `school` VALUES ('84', '17', '湖南师范大学', '84', '2015-04-20 03:44:08', '2015-04-20 03:44:08');
INSERT INTO `school` VALUES ('85', '17', '衡阳师范学院', '85', '2015-04-20 03:44:08', '2015-04-20 03:44:08');
INSERT INTO `school` VALUES ('86', '17', '湖南人文科技学院', '86', '2015-04-20 03:44:08', '2015-04-20 03:44:08');
INSERT INTO `school` VALUES ('87', '17', '湖南师范大学树达学院', '87', '2015-04-20 03:44:09', '2015-04-20 03:44:09');
INSERT INTO `school` VALUES ('88', '17', '衡阳师范学院南岳学院', '88', '2015-04-20 03:44:09', '2015-04-20 03:44:09');
INSERT INTO `school` VALUES ('89', '17', '湘潭教育学院', '89', '2015-04-20 03:44:09', '2015-04-20 03:44:09');
INSERT INTO `school` VALUES ('90', '19', '东北师范大学', '90', '2015-04-20 03:44:09', '2015-04-20 03:44:09');
INSERT INTO `school` VALUES ('91', '19', '通化师范学院', '91', '2015-04-20 03:44:09', '2015-04-20 03:44:09');
INSERT INTO `school` VALUES ('92', '19', '吉林师范大学', '92', '2015-04-20 03:44:09', '2015-04-20 03:44:09');
INSERT INTO `school` VALUES ('93', '19', '吉林工程技术师范学院', '93', '2015-04-20 03:44:09', '2015-04-20 03:44:09');
INSERT INTO `school` VALUES ('94', '19', '长春师范学院', '94', '2015-04-20 03:44:09', '2015-04-20 03:44:09');
INSERT INTO `school` VALUES ('95', '19', '白城师范学院', '95', '2015-04-20 03:44:09', '2015-04-20 03:44:09');
INSERT INTO `school` VALUES ('96', '19', '吉林师范大学博达学院', '96', '2015-04-20 03:44:09', '2015-04-20 03:44:09');
INSERT INTO `school` VALUES ('97', '21', '南京师范大学', '97', '2015-04-20 03:44:09', '2015-04-20 03:44:09');
INSERT INTO `school` VALUES ('98', '21', '徐州师范大学', '98', '2015-04-20 03:44:09', '2015-04-20 03:44:09');
INSERT INTO `school` VALUES ('99', '21', '淮阴师范学院', '99', '2015-04-20 03:44:09', '2015-04-20 03:44:09');
INSERT INTO `school` VALUES ('100', '21', '盐城师范学院', '100', '2015-04-20 03:44:09', '2015-04-20 03:44:09');
INSERT INTO `school` VALUES ('101', '21', '南京晓庄学院', '101', '2015-04-20 03:44:09', '2015-04-20 03:44:09');
INSERT INTO `school` VALUES ('102', '21', '江苏技术师范学院', '102', '2015-04-20 03:44:09', '2015-04-20 03:44:09');
INSERT INTO `school` VALUES ('103', '21', '南京师范大学泰州学院', '103', '2015-04-20 03:44:09', '2015-04-20 03:44:09');
INSERT INTO `school` VALUES ('104', '48', '赣南师范学院科技学院', '104', '2015-04-20 03:44:09', '2015-04-20 03:44:09');
INSERT INTO `school` VALUES ('105', '48', '江西师范大学', '105', '2015-04-20 03:44:09', '2015-04-20 03:44:09');
INSERT INTO `school` VALUES ('106', '48', '上饶师范学院', '106', '2015-04-20 03:44:10', '2015-04-20 03:44:10');
INSERT INTO `school` VALUES ('107', '48', '赣南师范学院', '107', '2015-04-20 03:44:10', '2015-04-20 03:44:10');
INSERT INTO `school` VALUES ('108', '48', '江西科技师范学院', '108', '2015-04-20 03:44:10', '2015-04-20 03:44:10');
INSERT INTO `school` VALUES ('109', '23', '辽宁师范大学', '109', '2015-04-20 03:44:10', '2015-04-20 03:44:10');
INSERT INTO `school` VALUES ('110', '23', '沈阳师范大学', '110', '2015-04-20 03:44:10', '2015-04-20 03:44:10');
INSERT INTO `school` VALUES ('111', '23', '鞍山师范学院', '111', '2015-04-20 03:44:10', '2015-04-20 03:44:10');
INSERT INTO `school` VALUES ('112', '23', '辽宁师范大学海华学院', '112', '2015-04-20 03:44:10', '2015-04-20 03:44:10');
INSERT INTO `school` VALUES ('113', '26', '内蒙古师范大学', '113', '2015-04-20 03:44:10', '2015-04-20 03:44:10');
INSERT INTO `school` VALUES ('114', '26', '集宁师范学院', '114', '2015-04-20 03:44:10', '2015-04-20 03:44:10');
INSERT INTO `school` VALUES ('115', '26', '内蒙古师范大学鸿德学院', '115', '2015-04-20 03:44:10', '2015-04-20 03:44:10');
INSERT INTO `school` VALUES ('116', '49', '宁夏师范学院', '116', '2015-04-20 03:44:10', '2015-04-20 03:44:10');
INSERT INTO `school` VALUES ('117', '50', '青海师范大学', '117', '2015-04-20 03:44:10', '2015-04-20 03:44:10');
INSERT INTO `school` VALUES ('118', '28', '聊城大学', '118', '2015-04-20 03:44:10', '2015-04-20 03:44:10');
INSERT INTO `school` VALUES ('119', '28', '潍坊学院', '119', '2015-04-20 03:44:10', '2015-04-20 03:44:10');
INSERT INTO `school` VALUES ('120', '28', '枣庄学院', '120', '2015-04-20 03:44:10', '2015-04-20 03:44:10');
INSERT INTO `school` VALUES ('121', '28', '山东科技大学', '121', '2015-04-20 03:44:10', '2015-04-20 03:44:10');
INSERT INTO `school` VALUES ('122', '28', '青岛大学', '122', '2015-04-20 03:44:10', '2015-04-20 03:44:10');
INSERT INTO `school` VALUES ('123', '28', '济宁医学院', '123', '2015-04-20 03:44:10', '2015-04-20 03:44:10');
INSERT INTO `school` VALUES ('124', '28', '烟台大学', '124', '2015-04-20 03:44:10', '2015-04-20 03:44:10');
INSERT INTO `school` VALUES ('125', '28', '鲁东大学', '125', '2015-04-20 03:44:11', '2015-04-20 03:44:11');
INSERT INTO `school` VALUES ('126', '28', '济南大学', '126', '2015-04-20 03:44:11', '2015-04-20 03:44:11');
INSERT INTO `school` VALUES ('127', '28', '山东师范大学', '127', '2015-04-20 03:44:11', '2015-04-20 03:44:11');
INSERT INTO `school` VALUES ('128', '28', '曲阜师范大学', '128', '2015-04-20 03:44:11', '2015-04-20 03:44:11');
INSERT INTO `school` VALUES ('129', '28', '滨州学院', '129', '2015-04-20 03:44:11', '2015-04-20 03:44:11');
INSERT INTO `school` VALUES ('130', '28', '临沂大学', '130', '2015-04-20 03:44:11', '2015-04-20 03:44:11');
INSERT INTO `school` VALUES ('131', '28', '济宁学院', '131', '2015-04-20 03:44:11', '2015-04-20 03:44:11');
INSERT INTO `school` VALUES ('132', '28', '齐鲁师范学院', '132', '2015-04-20 03:44:11', '2015-04-20 03:44:11');
INSERT INTO `school` VALUES ('133', '28', '山东教育学院', '133', '2015-04-20 03:44:11', '2015-04-20 03:44:11');
INSERT INTO `school` VALUES ('134', '33', '大同大学', '134', '2015-04-20 03:44:11', '2015-04-20 03:44:11');
INSERT INTO `school` VALUES ('135', '33', '山西师范大学', '135', '2015-04-20 03:44:11', '2015-04-20 03:44:11');
INSERT INTO `school` VALUES ('136', '33', '太原师范学院', '136', '2015-04-20 03:44:11', '2015-04-20 03:44:11');
INSERT INTO `school` VALUES ('137', '33', '晋中学院', '137', '2015-04-20 03:44:11', '2015-04-20 03:44:11');
INSERT INTO `school` VALUES ('138', '33', '长治学院', '138', '2015-04-20 03:44:11', '2015-04-20 03:44:11');
INSERT INTO `school` VALUES ('139', '33', '运城学院', '139', '2015-04-20 03:44:12', '2015-04-20 03:44:12');
INSERT INTO `school` VALUES ('140', '33', '忻州师范学院', '140', '2015-04-20 03:44:12', '2015-04-20 03:44:12');
INSERT INTO `school` VALUES ('141', '33', '吕梁学院', '141', '2015-04-20 03:44:12', '2015-04-20 03:44:12');
INSERT INTO `school` VALUES ('142', '33', '山西师范大学现代文理学院', '142', '2015-04-20 03:44:12', '2015-04-20 03:44:12');
INSERT INTO `school` VALUES ('143', '35', '西安电子科技大学', '143', '2015-04-20 03:44:12', '2015-04-20 03:44:12');
INSERT INTO `school` VALUES ('144', '35', '商洛学院', '144', '2015-04-20 03:44:12', '2015-04-20 03:44:12');
INSERT INTO `school` VALUES ('145', '35', '学前师范', '145', '2015-04-20 03:44:12', '2015-04-20 03:44:12');
INSERT INTO `school` VALUES ('146', '35', '西安文理学院', '146', '2015-04-20 03:44:12', '2015-04-20 03:44:12');
INSERT INTO `school` VALUES ('147', '35', '榆林学院', '147', '2015-04-20 03:44:12', '2015-04-20 03:44:12');
INSERT INTO `school` VALUES ('148', '35', '陕西师范大学', '148', '2015-04-20 03:44:12', '2015-04-20 03:44:12');
INSERT INTO `school` VALUES ('149', '35', '宝鸡文理学院', '149', '2015-04-20 03:44:12', '2015-04-20 03:44:12');
INSERT INTO `school` VALUES ('150', '35', '咸阳师范学院', '150', '2015-04-20 03:44:13', '2015-04-20 03:44:13');
INSERT INTO `school` VALUES ('151', '35', '渭南师范学院', '151', '2015-04-20 03:44:13', '2015-04-20 03:44:13');
INSERT INTO `school` VALUES ('152', '37', '华东师范大学', '152', '2015-04-20 03:44:13', '2015-04-20 03:44:13');
INSERT INTO `school` VALUES ('153', '37', '上海师范大学', '153', '2015-04-20 03:44:13', '2015-04-20 03:44:13');
INSERT INTO `school` VALUES ('154', '39', '西华师范大学', '154', '2015-04-20 03:44:13', '2015-04-20 03:44:13');
INSERT INTO `school` VALUES ('155', '39', '绵阳师范学院', '155', '2015-04-20 03:44:13', '2015-04-20 03:44:13');
INSERT INTO `school` VALUES ('156', '39', '内江师范学院', '156', '2015-04-20 03:44:14', '2015-04-20 03:44:14');
INSERT INTO `school` VALUES ('157', '39', '乐山师范学院', '157', '2015-04-20 03:44:14', '2015-04-20 03:44:14');
INSERT INTO `school` VALUES ('158', '39', '四川民族学院', '158', '2015-04-20 03:44:14', '2015-04-20 03:44:14');
INSERT INTO `school` VALUES ('159', '39', '四川师范大学文理学院', '159', '2015-04-20 03:44:15', '2015-04-20 03:44:15');
INSERT INTO `school` VALUES ('160', '39', '四川师范大学', '160', '2015-04-20 03:44:15', '2015-04-20 03:44:15');
INSERT INTO `school` VALUES ('161', '41', '天津师范大学', '161', '2015-04-20 03:44:15', '2015-04-20 03:44:15');
INSERT INTO `school` VALUES ('162', '51', '香港教育学院', '162', '2015-04-20 03:44:15', '2015-04-20 03:44:15');
INSERT INTO `school` VALUES ('163', '52', '新疆师范大学', '163', '2015-04-20 03:44:15', '2015-04-20 03:44:15');
INSERT INTO `school` VALUES ('164', '52', '喀什师范学院', '164', '2015-04-20 03:44:15', '2015-04-20 03:44:15');
INSERT INTO `school` VALUES ('165', '52', '伊犁师范学院', '165', '2015-04-20 03:44:15', '2015-04-20 03:44:15');
INSERT INTO `school` VALUES ('166', '52', '昌吉学院', '166', '2015-04-20 03:44:15', '2015-04-20 03:44:15');
INSERT INTO `school` VALUES ('167', '53', '云南师范大学', '167', '2015-04-20 03:44:15', '2015-04-20 03:44:15');
INSERT INTO `school` VALUES ('168', '53', '曲靖师范学院', '168', '2015-04-20 03:44:15', '2015-04-20 03:44:15');
INSERT INTO `school` VALUES ('169', '53', '保山学院', '169', '2015-04-20 03:44:15', '2015-04-20 03:44:15');
INSERT INTO `school` VALUES ('170', '53', '玉溪师范学院', '170', '2015-04-20 03:44:15', '2015-04-20 03:44:15');
INSERT INTO `school` VALUES ('171', '53', '楚雄师范学院', '171', '2015-04-20 03:44:15', '2015-04-20 03:44:15');
INSERT INTO `school` VALUES ('172', '53', '文山学院', '172', '2015-04-20 03:44:15', '2015-04-20 03:44:15');
INSERT INTO `school` VALUES ('173', '54', '温州大学瓯江学院', '173', '2015-04-20 03:44:15', '2015-04-20 03:44:15');
INSERT INTO `school` VALUES ('174', '54', '浙江师范大学', '174', '2015-04-20 03:44:15', '2015-04-20 03:44:15');
INSERT INTO `school` VALUES ('175', '54', '杭州师范大学', '175', '2015-04-20 03:44:15', '2015-04-20 03:44:15');
INSERT INTO `school` VALUES ('176', '54', '湖州师范学院', '176', '2015-04-20 03:44:15', '2015-04-20 03:44:15');
INSERT INTO `school` VALUES ('177', '54', '绍兴文理学院', '177', '2015-04-20 03:44:15', '2015-04-20 03:44:15');
INSERT INTO `school` VALUES ('178', '54', '丽水学院', '178', '2015-04-20 03:44:16', '2015-04-20 03:44:16');
INSERT INTO `school` VALUES ('179', '54', '杭州师范大学钱江学院', '179', '2015-04-20 03:44:16', '2015-04-20 03:44:16');
INSERT INTO `school` VALUES ('180', '54', '湖州师范学院求真学院', '180', '2015-04-20 03:44:16', '2015-04-20 03:44:16');
INSERT INTO `school` VALUES ('181', '54', '绍兴文理学院元培学院', '181', '2015-04-20 03:44:16', '2015-04-20 03:44:16');



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



/**-------------------------------老陆-------------------------------------------------------**/

drop table if exists rating;

/*==============================================================*/
/* Table: rating                                                */
/*==============================================================*/
create table rating
(
   id                   tinyint(4) not null auto_increment comment '主键',
   star                 tinyint(4) not null comment '星级 1,2,3,4,5',
   name                 varchar(100),
   description          varchar(200) comment '描述',
   points               int comment '星级对应积分',
   primary key (id)
)DEFAULT CHARSET=utf8;

alter table rating comment '星级特权';



drop table if exists dictionary;

/*==============================================================*/
/* Table: dictionary                                            */
/*==============================================================*/
create table dictionary
(
   id                   int not null auto_increment comment '主键',
   type                 int not null comment '字典类型 1：性别 2：教师身份 3：辅导时间',
   name                 varchar(64) comment '标题',
   value                varchar(32) comment '值',
   create_time          timestamp not null comment '创建时间',
   sort_no              int,
   primary key (id)
)DEFAULT CHARSET=utf8;

alter table dictionary comment '数据字段，保存通用数据';

drop table if exists evaluation;

/*==============================================================*/
/* Table: evaluation                                            */
/*==============================================================*/
create table evaluation
(
   id                   int not null auto_increment comment '主键',
   level                int comment '评价级别',
   name                 varchar(100) comment '好评',
   description          varchar(512) comment '描述',
   point                int comment '评价得积分',
   teacher_identify     int(11) comment '教师身份 1、教师；2、学生',
   primary key (id)
)DEFAULT CHARSET=utf8;

alter table evaluation comment '评价定义';


drop table if exists point_offline;

/*==============================================================*/
/* Table: point_offline                                         */
/*==============================================================*/
create table point_offline
(
   id                   int not null auto_increment comment '主键',
   name                 varchar(100) comment '标题',
   description          varchar(512) comment '描述',
   money                int comment '线下收入',
   point                int comment '线下收入抵积分',
   primary key (id)
)DEFAULT CHARSET=utf8;

alter table point_offline comment '离线积分';

drop table if exists rating_income_config;

/*==============================================================*/
/* Table: rating_income_config                                  */
/*==============================================================*/
create table rating_income_config
(
   id                   int(11) not null auto_increment,
   star                 tinyint comment '星级',
   teacher_identify     int(11) comment '教师身份 1、教师；2、学生',
   fee                  int comment '费用，单位是分',
   description          varchar(256),
   primary key (id)
)DEFAULT CHARSET=utf8;

#字典数据
insert into dictionary(type,name,value) values(1,'男',1);
insert into dictionary(type,name,value) values(1,'女',2);

insert into dictionary(type,name,value) values(2,'老师',1);
insert into dictionary(type,name,value) values(2,'学生',2);

insert into dictionary(type,name,value,sort_no) values(3,'周一',100,1);
insert into dictionary(type,name,value,sort_no) values(3,'上午',101,2);
insert into dictionary(type,name,value,sort_no) values(3,'下午',102,3);
insert into dictionary(type,name,value,sort_no) values(3,'晚上',103,4);
insert into dictionary(type,name,value,sort_no) values(3,'周二',200,5);
insert into dictionary(type,name,value,sort_no) values(3,'上午',201,6);
insert into dictionary(type,name,value,sort_no) values(3,'下午',202,7);
insert into dictionary(type,name,value,sort_no) values(3,'晚上',203,8);
insert into dictionary(type,name,value,sort_no) values(3,'周三',300,9);
insert into dictionary(type,name,value,sort_no) values(3,'上午',301,10);
insert into dictionary(type,name,value,sort_no) values(3,'下午',302,11);
insert into dictionary(type,name,value,sort_no) values(3,'晚上',303,12);
insert into dictionary(type,name,value,sort_no) values(3,'周四',400,13);
insert into dictionary(type,name,value,sort_no) values(3,'上午',401,14);
insert into dictionary(type,name,value,sort_no) values(3,'下午',402,15);
insert into dictionary(type,name,value,sort_no) values(3,'晚上',403,16);
insert into dictionary(type,name,value,sort_no) values(3,'周五',500,17);
insert into dictionary(type,name,value,sort_no) values(3,'上午',501,18);
insert into dictionary(type,name,value,sort_no) values(3,'下午',502,19);
insert into dictionary(type,name,value,sort_no) values(3,'晚上',503,20);
insert into dictionary(type,name,value,sort_no) values(3,'周六',600,21);
insert into dictionary(type,name,value,sort_no) values(3,'上午',601,22);
insert into dictionary(type,name,value,sort_no) values(3,'下午',602,23);
insert into dictionary(type,name,value,sort_no) values(3,'晚上',603,24);
insert into dictionary(type,name,value,sort_no) values(3,'周日',700,25);
insert into dictionary(type,name,value,sort_no) values(3,'上午',701,26);
insert into dictionary(type,name,value,sort_no) values(3,'下午',702,27);
insert into dictionary(type,name,value,sort_no) values(3,'晚上',703,28);

#星级特权定义
insert into rating(star,name,description,points) values(0,'0星','需要+%s积分',  500);
insert into rating(star,name,description,points) values(1,'1星','需要+%s积分',  500);
insert into rating(star,name,description,points) values(2,'2星','需要+%s积分',1500);
insert into rating(star,name,description,points) values(3,'3星','需要+%s积分',3000);
insert into rating(star,name,description,points) values(4,'4星','需要+%s积分',5000);
insert into rating(star,name,description,points) values(5,'5星','需要+%s积分',7000);

#评价定义
insert into evaluation(level,name,description,point,teacher_identify) values(1,'好评','音频被审核为好评',10,1);
insert into evaluation(level,name,description,point,teacher_identify) values(2,'中评','音频被审核为中评',5,1);
insert into evaluation(level,name,description,point,teacher_identify) values(3,'差评','音频被审核为差评',-30,1);
insert into evaluation(level,name,description,point,teacher_identify) values(1,'好评','音频被审核为好评',10,2);
insert into evaluation(level,name,description,point,teacher_identify) values(2,'中评','音频被审核为中评',5,2);
insert into evaluation(level,name,description,point,teacher_identify) values(3,'差评','音频被审核为差评',-30,2);

#插入线下积分定义
insert into point_offline(name,description,money,point) values('','每增加%s元收入 +%s积分',2000,50);

#插入科目
#truncate subject;
#insert into subject(name,real_subject,sort_number) values('数学',1,1);
#insert into subject(name,real_subject,sort_number) values('语文',2,1);
#insert into subject(name,real_subject,sort_number) values('英语',3,1);
#insert into subject(name,real_subject,sort_number) values('政治',4,1);
#insert into subject(name,real_subject,sort_number) values('历史',5,1);
#insert into subject(name,real_subject,sort_number) values('地理',6,1);
#insert into subject(name,real_subject,sort_number) values('物理',7,1);
#insert into subject(name,real_subject,sort_number) values('化学',8,1);
#insert into subject(name,real_subject,sort_number) values('生物',9,1);

truncate rating_income_config;
insert into rating_income_config(star,teacher_identify,fee,description) values(0,1,0,'【每日任务】每录制一条音频');
insert into rating_income_config(star,teacher_identify,fee,description) values(1,1,0,'【每日任务】每录制一条音频');
insert into rating_income_config(star,teacher_identify,fee,description) values(2,1,0,'【每日任务】每录制一条音频');
insert into rating_income_config(star,teacher_identify,fee,description) values(3,1,0,'【每日任务】每录制一条音频');
insert into rating_income_config(star,teacher_identify,fee,description) values(4,1,0,'【每日任务】每录制一条音频');
insert into rating_income_config(star,teacher_identify,fee,description) values(5,1,0,'【每日任务】每录制一条音频');
insert into rating_income_config(star,teacher_identify,fee,description) values(0,2,0,'【每日任务】每录制一条音频');
insert into rating_income_config(star,teacher_identify,fee,description) values(1,2,100,'【每日任务】每录制一条音频');
insert into rating_income_config(star,teacher_identify,fee,description) values(2,2,200,'【每日任务】每录制一条音频');
insert into rating_income_config(star,teacher_identify,fee,description) values(3,2,300,'【每日任务】每录制一条音频');
insert into rating_income_config(star,teacher_identify,fee,description) values(4,2,400,'【每日任务】每录制一条音频');
insert into rating_income_config(star,teacher_identify,fee,description) values(5,2,500,'【每日任务】每录制一条音频');


/**---------------------------------------------------------**/
update question set source='1' where ifnull(source,'')='';

/***-----------------------------国庆-----------------------------------**/
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




#教师端后台提供，  biz库
#题目关联机构表，录题后台会定时插入线上题目与机构id相关联的表内


#以下是录题后台使用的关联表
#orc_ops_sources  biz库
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


ALTER TABLE audio_approve ADD UNIQUE (audio_id);


/*==============================================================*/
/* Table: feud_point_fee_conf                                   */
/*==============================================================*/
create table feud_point_fee_conf
(
   id                   int(11) not null auto_increment,
   star_evaluate        int(11) comment '星级或者评价 星级 3,4,5 评价: 1.好 2中 3差',
   teacher_identify     int(11) comment '教师身份 1、教师；2、学生',
   value                bigint(20) comment '费用，单位是分 或者 积分 ',
   conf_type            int(11) comment '配置类型: 1,积分 2 抢答费',
   description          varchar(20) comment '教师身份 星级 及 积分或者抢答费的组合描述',
   primary key (id)
)DEFAULT CHARSET=utf8;

alter table feud_point_fee_conf comment '抢答积分抢答费配置表';

insert feud_point_fee_conf(star_evaluate,teacher_identify,value,conf_type,description)
values(1,1,10,1,'抢答被审核为好评');


insert feud_point_fee_conf(star_evaluate,teacher_identify,value,conf_type,description)
values(3,1,-30,1,'抢答被审核为差评');

insert feud_point_fee_conf(star_evaluate,teacher_identify,value,conf_type,description)
values(1,2,10,1,'抢答被审核为好评');


insert feud_point_fee_conf(star_evaluate,teacher_identify,value,conf_type,description)
values(3,2,-30,1,'抢答被审核为差评');


insert feud_point_fee_conf(star_evaluate,teacher_identify,value,conf_type,description)
values(-1,1,-10,1,'逾期未提交/放弃抢答');
insert feud_point_fee_conf(star_evaluate,teacher_identify,value,conf_type,description)
values(-1,2,-10,1,'逾期未提交/放弃抢答');



insert feud_point_fee_conf(star_evaluate,teacher_identify,value,conf_type,description)
values(3,1,600,2,'【抢　　答】每完成一次抢答');

insert feud_point_fee_conf(star_evaluate,teacher_identify,value,conf_type,description)
values(4,1,800,2,'【抢　　答】每完成一次抢答');

insert feud_point_fee_conf(star_evaluate,teacher_identify,value,conf_type,description)
values(5,1,1000,2,'【抢　　答】每完成一次抢答');

insert feud_point_fee_conf(star_evaluate,teacher_identify,value,conf_type,description)
values(3,2,600,2,'【抢　　答】每完成一次抢答');

insert feud_point_fee_conf(star_evaluate,teacher_identify,value,conf_type,description)
values(4,2,800,2,'【抢　　答】每完成一次抢答');

insert feud_point_fee_conf(star_evaluate,teacher_identify,value,conf_type,description)
values(5,2,1000,2,'【抢　　答】每完成一次抢答');

/**--------------------------明宝--------------------------------------------------------**/	

#oldlu 20150402 音频是否需要后台评价
alter table audio_approve add column evalution tinyint(4);


#费用变化表
drop table if exists fee_log;

/*==============================================================*/
/* Table: fee_log                                               */
/*==============================================================*/
create table fee_log
(
   id                   bigint not null auto_increment comment '主键',
   money                int comment '费用',
   teacher_id           varchar(50) comment '教师ID',
   description          varchar(256) comment '费用变化描述',
   create_time          timestamp,
   update_time          timestamp,
   primary key (id)
)DEFAULT CHARSET=utf8;

alter table fee_log comment '费用变化日志';



#机构定向推送变化
#1、biz库涉及表结构变化
drop table if exists org_quest;
create table org_quest
(
   org_id               int not null comment '机构id',
   real_quest_id        bigint not null comment '题库真实ID',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '修改时间',
   primary key (org_id, real_quest_id)
)DEFAULT CHARSET=utf8;
alter table org_quest comment '机构与题库关系';

#机构表增加教师ID和教师账号，用于绑定
alter table organization_sources add column teacher_id varchar(50);
alter table organization_sources add column teacher_phone_number varchar(32);

#音频上传表增加机构ID
alter table audio_upload add column org_id int;
alter table audio_upload add column org_teacher_id varchar(50);

#2、pay库涉及表结构变化
#音频库表增加机构ID
#alter table audio add column org_id int;
#alter table audio add column org_teacher_id varchar(50);

#索引变化
alter table question add index idx_audio_upload_status(audio_upload_status,source);

alter table feedback modify phone_number varchar(32);

alter table point_log add remain_point int default 0 comment '每次积分变化后的结余积分';

alter table question add column allot_status tinyint default 0 comment '是否已分配 0:未分配 1：已分配';


#题库分配数据变化
update  question a,question_allot b
		set a.allot_status=1
		where a.real_id=b.question_id;
		
		
create table verify_codes
(
   phone_number         national varchar(20) not null comment '手机号',
   verify_code          national varchar(20) not null comment '验证码',
   send_time            timestamp not null default CURRENT_TIMESTAMP comment '发送时间',
   primary key (phone_number),
   key uniq_verify_codes_phone_number (phone_number)
)DEFAULT CHARSET=utf8;

alter table verify_codes comment '验证码表';

ALTER TABLE audio_upload ADD type  int COMMENT '1 audio,2 whiteboard';
ALTER TABLE audio_upload ADD source int COMMENT 'task 1.每日任务,2.tall 微店,3.feud 抢答';


#######表索引###
ALTER TABLE push_msg_persistent ADD INDEX index_push_msg_is_read ( is_read );

ALTER TABLE push_msg_persistent ADD INDEX index_push_msg_user_id ( user_id );

ALTER TABLE teacher_followed ADD INDEX index_teacher_followed_user_id ( user_id );

ALTER TABLE teacher_grade ADD INDEX index_teacher_grade_tid ( teacher_id );

ALTER TABLE teacher_subject ADD INDEX index_teacher_subject_tid ( teacher_id );

ALTER TABLE feud_answer_detail ADD INDEX index_answer_detail_tid (teacher_id);

ALTER TABLE feud_answer_detail ADD INDEX index_answer_detail_quest_id (feud_quest_id);

ALTER TABLE feud_quest ADD INDEX index_feud_quest_student_id (student_id,question_real_id);

ALTER TABLE feud_quest ADD INDEX index_feud_quest_status (status);

ALTER TABLE feud_quest ADD INDEX index_feud_quest_create_time (create_time);

ALTER TABLE question ADD INDEX index_question_real_subject (real_subject);

ALTER TABLE feud_detail_wb ADD INDEX index_feud_detail_wb_id (wb_id);


ALTER TABLE system_user ADD INDEX index_system_user_account (user_account);

ALTER TABLE system_user ADD INDEX index_system_user_phone_number (phone_number);

create index learnTalkDetailIndex on learn_message(teacher_id,student_id,teacher_offer_id,teacher_offer_type);

create index learnTalkIndex on learn_message(teacher_id,send_msg_user_type,msg_type,teacher_offer_id); 



alter table question add column allot_status tinyint default 0 comment '是否已分配 0:未分配 1：已分配';


#题库分配数据变化
update  question a,question_allot b
		set a.allot_status=1
		where a.real_id=b.question_id  ;


		
		
			



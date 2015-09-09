/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2015/3/30 18:46:04                           */
/*==============================================================*/


drop table if exists audio_student_evaluation;

drop table if exists batch_course_img;

drop table if exists education_biz.course_upload_batch;

drop table if exists education_biz.course_ware;

drop table if exists education_biz.course_ware_evaluation;

drop table if exists education_biz.course_ware_grade;

drop table if exists education_biz.course_ware_subject;

drop table if exists dictionary;

drop table if exists evaluation;

drop table if exists feedback;

drop table if exists feud_answer_detail;

drop table if exists feud_quest;

drop table if exists grade_subject;

drop table if exists learn_message;

drop table if exists point_offline;

drop table if exists rating;

drop table if exists education_biz.teacher_grade;

drop table if exists education_biz.teacher_subject;

drop table if exists education_biz.vip_teacher;

drop table if exists education_biz.vip_teacher_time;

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
);

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
);

alter table batch_course_img comment '课件批量上传图片表
';

/*==============================================================*/
/* Table: course_upload_batch                                   */
/*==============================================================*/
create table education_biz.course_upload_batch
(
   id                   bigint not null auto_increment,
   create_time          datetime comment '创建时间',
   update_time          timestamp comment '修改时间',
   batch_id             varchar(50) comment '上传批次',
   status               int comment '批次状态 1未完成，2已经完成',
   teacher_id           varchar(50) comment '教师id',
   primary key (id)
);

alter table education_biz.course_upload_batch comment '上传批次表';

/*==============================================================*/
/* Table: course_ware                                           */
/*==============================================================*/
create table education_biz.course_ware
(
   id                   bigint not null auto_increment,
   teacher_id           varchar(50) comment '名师汇tearcher Id',
   name                 varchar(100) comment '课件名',
   price                int comment '价格',
   introduce            varchar(2000) comment '介绍',
   create_time          datetime comment '创建时间',
   update_tiime         timestamp comment '修改时间',
   status               varchar(10) comment '状态 1位发布，2已发布',
   version              int comment '上线的版本',
   is_delete            char comment '是否删除',
   primary key (id)
);

alter table education_biz.course_ware comment '课件表';

/*==============================================================*/
/* Table: course_ware_evaluation                                */
/*==============================================================*/
create table education_biz.course_ware_evaluation
(
   id                   bigint not null auto_increment,
   course_ware_id       bigint comment '课件id',
   user_id              varchar(50) comment '用户id',
   content              varchar(1000) comment '评价内容',
   star                 tinyint comment '星级',
   create_time          datetime comment '创建时间',
   update_time          timestamp comment '更新时间',
   primary key (id)
);

alter table education_biz.course_ware_evaluation comment '课件评价';

/*==============================================================*/
/* Table: course_ware_grade                                     */
/*==============================================================*/
create table education_biz.course_ware_grade
(
   id                   bigint not null auto_increment,
   course_ware_id       bigint comment '课件id',
   grade_id             bigint comment '年级id',
   teacher_id           varchar(50) comment '老师id',
   create_time          datetime,
   update_time          timestamp,
   primary key (id)
);

alter table education_biz.course_ware_grade comment '课件年级关联表';

/*==============================================================*/
/* Table: course_ware_subject                                   */
/*==============================================================*/
create table education_biz.course_ware_subject
(
   id                   bigint not null auto_increment,
   course_ware_id       bigint comment '课件id',
   subject_id           bigint comment '学科id',
   teacher_id           varchar(50) comment '老师id',
   create_time          datetime,
   update_time          timestamp,
   primary key (id)
);

alter table education_biz.course_ware_subject comment '课件学科表';

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
);

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
   primary key (id)
);

alter table evaluation comment '评价定义';

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
);

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
   create_time          datetime,
   update_time          timestamp,
   status               int comment '抢答状态 1待提交，2已提交，3已失效',
   quest_real_id        bigint(20) comment '问题ID',
   evaluate             int(5) comment '评价,1 好评,2 中评,3 差评',
   content              varchar(100) comment '评价内容',
   primary key (id)
);

alter table feud_answer_detail comment '教师抢答明细表


';

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
);

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
);

alter table grade_subject comment '年级与学科对应';

/*==============================================================*/
/* Table: learn_message                                         */
/*==============================================================*/
create table learn_message
(
   id                   bigint(20) not null auto_increment,
   teacher_id           varchar(50),
   student_id           varchar(50),
   send_msg_user_type   int(5) comment '消息发送者 1 teacher,2 student',
   msg_type             int(5) comment '消息类型 1 txt ,2 img',
   teacher_offer_id     varchar(50) comment '请求地址	/api/minishop/getCourseWareDetail
            请求类型	POST
            请求参数	id:001
            boolean: withSaleMoney是否包括金额
            Boolean: witdhSaleNum 订购数
            Bolean: witdhStar 是否包括星级
            SEND COOKIE	
            返回结果	{
                "success": true,
                "msg": "",
                "data":
                {
                    "id":"int 课件id",
                    "name":"str 课件名",
                    "price":"int 课件价格",
                    "introduce":"str 课件价格",
                    "fitGrades":"str 适合年级",
                    "fitSubjects":"str 适合科目",
                    "star":"int 课件星级"，
                    "saleMoney":"int 销售金额",
                    "saleNum":"int 购买数"
                }
            }
            
            老师提供的课件音频白板的ID',
   content              varchar(255) comment '如果消息类型是图片,图片的URL,如果是文字则是文本内容',
   create_time          datetime,
   update_time          timestamp,
   teacher_offer_type   int comment '1 课件 2音频 3 白板',
   is_read              varchar(5) comment '是否阅读',
   primary key (id)
);

alter table learn_message comment '互动学习消息';

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
);

alter table point_offline comment '离线积分';

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
   income1              double(6,2) comment '好评奖励',
   income2              double(6,2) comment '中评奖励',
   primary key (id)
);

alter table rating comment '星级特权';

/*==============================================================*/
/* Table: teacher_grade                                         */
/*==============================================================*/
create table education_biz.teacher_grade
(
   id                   bigint not null auto_increment,
   grade_id             bigint comment '年级id',
   teacher_id           varchar(50) comment '老师id',
   create_time          datetime,
   update_time          timestamp,
   primary key (id)
);

alter table education_biz.teacher_grade comment '教师年级关联表';

/*==============================================================*/
/* Table: teacher_subject                                       */
/*==============================================================*/
create table education_biz.teacher_subject
(
   id                   bigint not null auto_increment,
   subject_id           bigint comment '学科id',
   teacher_id           varchar(50) comment '老师id',
   create_time          datetime,
   update_time          timestamp,
   primary key (id)
);

alter table education_biz.teacher_subject comment '教师学科表';

/*==============================================================*/
/* Table: vip_teacher                                           */
/*==============================================================*/
create table education_biz.vip_teacher
(
   id                   bigint not null auto_increment,
   teacher_id           varchar(50) comment '教师Id',
   apply_status         tinyint(4) comment '申请状态()',
   city_id              int comment '城市id',
   create_time          datetime comment '创建时间',
   update_time          timestamp comment '修改时间',
   primary key (id)
);

alter table education_biz.vip_teacher comment '名师汇老师';

/*==============================================================*/
/* Table: vip_teacher_time                                      */
/*==============================================================*/
create table education_biz.vip_teacher_time
(
   id                   bigint not null auto_increment,
   teacher_id           varchar(50) comment '老师id',
   time_id              bigint comment '授课时间id（来自dict）',
   create_time          datetime,
   update_time          timestamp,
   primary key (id)
);

alter table education_biz.vip_teacher_time comment '名师汇老师授课时间表';


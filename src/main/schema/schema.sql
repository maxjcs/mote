#oldlu 20150327 增加最后做任务时间
alter table teacher add column last_task_time  timestamp default current_timestamp;
#oldlu 20150402 增加是否完成新手任务标记
alter table teacher add column complete_new_user_task  tinyint default 0;

#oldlu 20150402 音频是否需要后台评价
alter table audio_upload add column need_evaluate  tinyint default 1;
alter table audio_approve add column evalution tinyint ;


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
);

alter table fee_log comment '费用变化日志';



#oldlu 20150407 增加最后一次登录设备号、设备类型
alter table teacher add column last_device_id varchar(100);
alter table teacher add column last_device_type varchar(100);
#增加在线状态
alter table teacher add column online_status varchar(1) ;



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

#机构定向推送变化
1、biz库涉及表结构变化
drop table if exists org_quest;
create table org_quest
(
   org_id               int not null comment '机构id',
   real_quest_id        bigint not null comment '题库真实ID',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '修改时间',
   primary key (org_id, real_quest_id)
);
alter table org_quest comment '机构与题库关系';

#机构表增加教师ID和教师账号，用于绑定
alter table organization_sources add column teacher_id varchar(50);
alter table organization_sources add column teacher_phone_number varchar(32);

#教师表增加当前归属机构ID
alter table teacher add column cur_org_id int;

#题库表增加题库来源
alter table question add column source varchar(1) comment '1：每日热题 2：抢答题 3：机构题';

#音频上传表增加机构ID
alter table audio_upload add column org_id int;
alter table audio_upload add column org_teacher_id varchar(50);

2、pay库涉及表结构变化
#音频库表增加机构ID
alter table audio add column org_id int;
alter table audio add column org_teacher_id varchar(50);

#索引变化
alter table question add index idx_audio_upload_status(audio_upload_status,source);


#赠送积分时间
alter table teacher add column capacity_test_status tinyint default 0 comment '0 未处理 1：已处理';
alter table teacher add column capacity_test_complete_time datetime comment '完成能力测试时间';
alter table teacher add column capacity_test_give_point int default 0 comment '能力测试赠送积分';

#是否参加新手任务
alter table teacher add column capacity_test_is_joined int default 0 comment '是否参加新手任务1不参加 0参加';

alter table teacher modify column self_description text;

alter table feedback modify phone_number varchar(32);

alter table point_log add remain_point int default 0 comment '每次积分变化后的结余积分';

alter table question add column allot_status tinyint default 0 comment '是否已分配 0:未分配 1：已分配';


#题库分配数据变化
update  question a,question_allot b
		set a.allot_status=1
		where a.real_id=b.question_id  ;
		
		
#更新音频库数据
update education_pay.audio  set source=1 where source is null;
update education_biz.audio_upload  set source=1 where source is null;

#20150505 oldlu 机构题库表增加科目，年段，状态字段
alter table org_quest add column status tinyint default 0 comment '题库状态 0 待分配 1：已分配 2：已上传';
alter table org_quest add column subject_id  int default 0  comment '科目ID';
alter table org_quest add column learn_phase varchar(32) default ''  comment '年段';

#20150505 oldlu 题库分配表增加机构ID用以记录分配的题目是哪个机构的
alter table question_allot add org_id int comment '机构ID';

#20150506 oldlu 根据最后登陆设备号创建索引
create index idx_teacher_last_device_id on  teacher(last_device_id);
#20150506 oldlu 根据教师ID创建题目分配索引
create index idx_question_allot_allot_user on  question_allot(allot_user);

#20150506 oldlu 更新老数据类型
update education_biz.audio_upload set type=1 where type is null;
update education_pay.audio set type=1 where type is null;

#20150507 oldlu 增加IOS消息通知token字段
 alter table teacher add column ios_token varchar(128) default ''  comment 'IOS消息推送字段';
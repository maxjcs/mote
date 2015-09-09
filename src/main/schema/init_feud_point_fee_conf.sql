/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2015/4/9 13:12:43                            */
/*==============================================================*/


drop table if exists feud_point_fee_conf;

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
);

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
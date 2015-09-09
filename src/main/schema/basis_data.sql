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
);

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
);

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
);

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
);

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
);

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
#insert into subject(name,real_subject,sort_number) values('化学',8,1);
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

 
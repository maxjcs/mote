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
   star_evaluate        int(11) comment '�Ǽ��������� �Ǽ� 3,4,5 ����: 1.�� 2�� 3��',
   teacher_identify     int(11) comment '��ʦ��� 1����ʦ��2��ѧ��',
   value                bigint(20) comment '���ã���λ�Ƿ� ���� ���� ',
   conf_type            int(11) comment '��������: 1,���� 2 �����',
   description          varchar(20) comment '��ʦ��� �Ǽ� �� ���ֻ�������ѵ��������',
   primary key (id)
);

alter table feud_point_fee_conf comment '���������������ñ�';

insert feud_point_fee_conf(star_evaluate,teacher_identify,value,conf_type,description)
values(1,1,10,1,'�������Ϊ����');


insert feud_point_fee_conf(star_evaluate,teacher_identify,value,conf_type,description)
values(3,1,-30,1,'�������Ϊ����');

insert feud_point_fee_conf(star_evaluate,teacher_identify,value,conf_type,description)
values(1,2,10,1,'�������Ϊ����');


insert feud_point_fee_conf(star_evaluate,teacher_identify,value,conf_type,description)
values(3,2,-30,1,'�������Ϊ����');


insert feud_point_fee_conf(star_evaluate,teacher_identify,value,conf_type,description)
values(-1,1,-10,1,'����δ�ύ/��������');
insert feud_point_fee_conf(star_evaluate,teacher_identify,value,conf_type,description)
values(-1,2,-10,1,'����δ�ύ/��������');



insert feud_point_fee_conf(star_evaluate,teacher_identify,value,conf_type,description)
values(3,1,600,2,'����������ÿ���һ������');

insert feud_point_fee_conf(star_evaluate,teacher_identify,value,conf_type,description)
values(4,1,800,2,'����������ÿ���һ������');

insert feud_point_fee_conf(star_evaluate,teacher_identify,value,conf_type,description)
values(5,1,1000,2,'����������ÿ���һ������');

insert feud_point_fee_conf(star_evaluate,teacher_identify,value,conf_type,description)
values(3,2,600,2,'����������ÿ���һ������');

insert feud_point_fee_conf(star_evaluate,teacher_identify,value,conf_type,description)
values(4,2,800,2,'����������ÿ���һ������');

insert feud_point_fee_conf(star_evaluate,teacher_identify,value,conf_type,description)
values(5,2,1000,2,'����������ÿ���һ������');
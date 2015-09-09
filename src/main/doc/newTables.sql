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
   id                   bigint(20) not null auto_increment comment '����',
   audio_id             varchar(50) comment '��ƵID',
   user_id              varchar(50) comment '�û�ID',
   star                 int comment '���ۼ���',
   content              varchar(400) comment '��������',
   create_time          timestamp comment '����ʱ��',
   primary key (id)
);

alter table audio_student_evaluation comment '��Ƶ����';

/*==============================================================*/
/* Table: batch_course_img                                      */
/*==============================================================*/
create table batch_course_img
(
   id                   bigint not null auto_increment,
   img_url              varchar(100) comment 'ͼƬurl',
   question_real_id     bigint comment 'ʶ����Ӧ������Id',
   create_time          datetime comment '����ʱ��',
   update_time          timestamp comment '����ʱ��',
   teacher_id           varchar(50) comment '��ʦ����ʦid',
   course_ware_id       bigint comment '�μ�id',
   batch_id             varchar(50) comment '����id',
   ocr_status           int comment 'ocr ״̬ 1δʶ�� 2 ʶ����� 3ʶ��ʧ��',
   audio_whiteboard_id  varchar(50) comment '��Ƶ�װ�id',
   primary key (id)
);

alter table batch_course_img comment '�μ������ϴ�ͼƬ��
';

/*==============================================================*/
/* Table: course_upload_batch                                   */
/*==============================================================*/
create table education_biz.course_upload_batch
(
   id                   bigint not null auto_increment,
   create_time          datetime comment '����ʱ��',
   update_time          timestamp comment '�޸�ʱ��',
   batch_id             varchar(50) comment '�ϴ�����',
   status               int comment '����״̬ 1δ��ɣ�2�Ѿ����',
   teacher_id           varchar(50) comment '��ʦid',
   primary key (id)
);

alter table education_biz.course_upload_batch comment '�ϴ����α�';

/*==============================================================*/
/* Table: course_ware                                           */
/*==============================================================*/
create table education_biz.course_ware
(
   id                   bigint not null auto_increment,
   teacher_id           varchar(50) comment '��ʦ��tearcher Id',
   name                 varchar(100) comment '�μ���',
   price                int comment '�۸�',
   introduce            varchar(2000) comment '����',
   create_time          datetime comment '����ʱ��',
   update_tiime         timestamp comment '�޸�ʱ��',
   status               varchar(10) comment '״̬ 1λ������2�ѷ���',
   version              int comment '���ߵİ汾',
   is_delete            char comment '�Ƿ�ɾ��',
   primary key (id)
);

alter table education_biz.course_ware comment '�μ���';

/*==============================================================*/
/* Table: course_ware_evaluation                                */
/*==============================================================*/
create table education_biz.course_ware_evaluation
(
   id                   bigint not null auto_increment,
   course_ware_id       bigint comment '�μ�id',
   user_id              varchar(50) comment '�û�id',
   content              varchar(1000) comment '��������',
   star                 tinyint comment '�Ǽ�',
   create_time          datetime comment '����ʱ��',
   update_time          timestamp comment '����ʱ��',
   primary key (id)
);

alter table education_biz.course_ware_evaluation comment '�μ�����';

/*==============================================================*/
/* Table: course_ware_grade                                     */
/*==============================================================*/
create table education_biz.course_ware_grade
(
   id                   bigint not null auto_increment,
   course_ware_id       bigint comment '�μ�id',
   grade_id             bigint comment '�꼶id',
   teacher_id           varchar(50) comment '��ʦid',
   create_time          datetime,
   update_time          timestamp,
   primary key (id)
);

alter table education_biz.course_ware_grade comment '�μ��꼶������';

/*==============================================================*/
/* Table: course_ware_subject                                   */
/*==============================================================*/
create table education_biz.course_ware_subject
(
   id                   bigint not null auto_increment,
   course_ware_id       bigint comment '�μ�id',
   subject_id           bigint comment 'ѧ��id',
   teacher_id           varchar(50) comment '��ʦid',
   create_time          datetime,
   update_time          timestamp,
   primary key (id)
);

alter table education_biz.course_ware_subject comment '�μ�ѧ�Ʊ�';

/*==============================================================*/
/* Table: dictionary                                            */
/*==============================================================*/
create table dictionary
(
   id                   int not null auto_increment comment '����',
   type                 int not null comment '�ֵ����� 1���Ա� 2����ʦ��� 3������ʱ��',
   name                 varchar(64) comment '����',
   value                varchar(32) comment 'ֵ',
   create_time          timestamp not null comment '����ʱ��',
   sort_no              int,
   primary key (id)
);

alter table dictionary comment '�����ֶΣ�����ͨ������';

/*==============================================================*/
/* Table: evaluation                                            */
/*==============================================================*/
create table evaluation
(
   id                   int not null auto_increment comment '����',
   level                int comment '���ۼ���',
   name                 varchar(100) comment '����',
   description          varchar(512) comment '����',
   point                int comment '���۵û���',
   primary key (id)
);

alter table evaluation comment '���۶���';

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

alter table feedback comment '�������';

/*==============================================================*/
/* Table: feud_answer_detail                                    */
/*==============================================================*/
create table feud_answer_detail
(
   id                   bigint(20) not null auto_increment,
   feud_quest_id        bigint(20) comment '����ID',
   teacher_id           varchar(50) comment '����teacher_id',
   feud_type            bigint(20) comment '1,audio,2 whiteboard',
   audio_whiteboard_id  varchar(50) comment 'audio_upload ���� whiteboard id  ��Ӧ audio_whiteboard_upload id',
   create_time          datetime,
   update_time          timestamp,
   status               int comment '����״̬ 1���ύ��2���ύ��3��ʧЧ',
   quest_real_id        bigint(20) comment '����ID',
   evaluate             int(5) comment '����,1 ����,2 ����,3 ����',
   content              varchar(100) comment '��������',
   primary key (id)
);

alter table feud_answer_detail comment '��ʦ������ϸ��


';

/*==============================================================*/
/* Table: feud_quest                                            */
/*==============================================================*/
create table feud_quest
(
   id                   bigint(20) not null auto_increment,
   question_real_id     bigint(20) comment '��ĿID',
   student_id           varchar(50) comment '���������ѧ���û�ID',
   image_id             varchar(50) comment '��ĿͼƬID',
   status               bigint(20) comment '״̬:1 ������,2 ������,3 ������,4 �ѹ���',
   feud_answer_teacher_id varchar(50) comment '�״������ʦID',
   feud_answer_detail_id bigint(20) comment '�״�������ϸID',
   create_time          datetime,
   update_time          timestamp,
   primary key (id)
);

alter table feud_quest comment '��������';

/*==============================================================*/
/* Table: grade_subject                                         */
/*==============================================================*/
create table grade_subject
(
   id                   int not null auto_increment,
   grade_id             int not null comment '�꼶id',
   subject_id           int not null comment 'ѧ��id',
   create_time          datetime,
   update_time          timestamp,
   primary key (id)
);

alter table grade_subject comment '�꼶��ѧ�ƶ�Ӧ';

/*==============================================================*/
/* Table: learn_message                                         */
/*==============================================================*/
create table learn_message
(
   id                   bigint(20) not null auto_increment,
   teacher_id           varchar(50),
   student_id           varchar(50),
   send_msg_user_type   int(5) comment '��Ϣ������ 1 teacher,2 student',
   msg_type             int(5) comment '��Ϣ���� 1 txt ,2 img',
   teacher_offer_id     varchar(50) comment '�����ַ	/api/minishop/getCourseWareDetail
            ��������	POST
            �������	id:001
            boolean: withSaleMoney�Ƿ�������
            Boolean: witdhSaleNum ������
            Bolean: witdhStar �Ƿ�����Ǽ�
            SEND COOKIE	
            ���ؽ��	{
                "success": true,
                "msg": "",
                "data":
                {
                    "id":"int �μ�id",
                    "name":"str �μ���",
                    "price":"int �μ��۸�",
                    "introduce":"str �μ��۸�",
                    "fitGrades":"str �ʺ��꼶",
                    "fitSubjects":"str �ʺϿ�Ŀ",
                    "star":"int �μ��Ǽ�"��
                    "saleMoney":"int ���۽��",
                    "saleNum":"int ������"
                }
            }
            
            ��ʦ�ṩ�Ŀμ���Ƶ�װ��ID',
   content              varchar(255) comment '�����Ϣ������ͼƬ,ͼƬ��URL,��������������ı�����',
   create_time          datetime,
   update_time          timestamp,
   teacher_offer_type   int comment '1 �μ� 2��Ƶ 3 �װ�',
   is_read              varchar(5) comment '�Ƿ��Ķ�',
   primary key (id)
);

alter table learn_message comment '����ѧϰ��Ϣ';

/*==============================================================*/
/* Table: point_offline                                         */
/*==============================================================*/
create table point_offline
(
   id                   int not null auto_increment comment '����',
   name                 varchar(100) comment '����',
   description          varchar(512) comment '����',
   money                int comment '��������',
   point                int comment '��������ֻ���',
   primary key (id)
);

alter table point_offline comment '���߻���';

/*==============================================================*/
/* Table: rating                                                */
/*==============================================================*/
create table rating
(
   id                   tinyint(4) not null auto_increment comment '����',
   star                 tinyint(4) not null comment '�Ǽ� 1,2,3,4,5',
   name                 varchar(100),
   description          varchar(200) comment '����',
   points               int comment '�Ǽ���Ӧ����',
   income1              double(6,2) comment '��������',
   income2              double(6,2) comment '��������',
   primary key (id)
);

alter table rating comment '�Ǽ���Ȩ';

/*==============================================================*/
/* Table: teacher_grade                                         */
/*==============================================================*/
create table education_biz.teacher_grade
(
   id                   bigint not null auto_increment,
   grade_id             bigint comment '�꼶id',
   teacher_id           varchar(50) comment '��ʦid',
   create_time          datetime,
   update_time          timestamp,
   primary key (id)
);

alter table education_biz.teacher_grade comment '��ʦ�꼶������';

/*==============================================================*/
/* Table: teacher_subject                                       */
/*==============================================================*/
create table education_biz.teacher_subject
(
   id                   bigint not null auto_increment,
   subject_id           bigint comment 'ѧ��id',
   teacher_id           varchar(50) comment '��ʦid',
   create_time          datetime,
   update_time          timestamp,
   primary key (id)
);

alter table education_biz.teacher_subject comment '��ʦѧ�Ʊ�';

/*==============================================================*/
/* Table: vip_teacher                                           */
/*==============================================================*/
create table education_biz.vip_teacher
(
   id                   bigint not null auto_increment,
   teacher_id           varchar(50) comment '��ʦId',
   apply_status         tinyint(4) comment '����״̬()',
   city_id              int comment '����id',
   create_time          datetime comment '����ʱ��',
   update_time          timestamp comment '�޸�ʱ��',
   primary key (id)
);

alter table education_biz.vip_teacher comment '��ʦ����ʦ';

/*==============================================================*/
/* Table: vip_teacher_time                                      */
/*==============================================================*/
create table education_biz.vip_teacher_time
(
   id                   bigint not null auto_increment,
   teacher_id           varchar(50) comment '��ʦid',
   time_id              bigint comment '�ڿ�ʱ��id������dict��',
   create_time          datetime,
   update_time          timestamp,
   primary key (id)
);

alter table education_biz.vip_teacher_time comment '��ʦ����ʦ�ڿ�ʱ���';


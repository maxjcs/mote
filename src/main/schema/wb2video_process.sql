
drop table if exists education_biz.wb2video_process;
/*==============================================================*/
/* User: education_biz                                          */
/*==============================================================*/


/*==============================================================*/
/* Table: wb2video_process                                       */
/*==============================================================*/
create table education_biz.wb2video_process
(
   id                   bigint(20) not null auto_increment comment '主键',
   teacher_id           varchar(64) not null comment '教师Id',
   video_url             varchar(64) comment '视频url',
   status               tinyint default 1 comment '1:上传成功 2:上传失败 3:转视频成功 4:转视频失败',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '更新时间',
   wb_id                varchar(128) comment 'wbid',
   primary key (id)
);

alter table education_biz.wb2video_process comment '白板转视频处理表';

alter table education_biz.audio_upload add column wb_type  tinyint default 1 comment '1:白板 2：视频';

alter table education_pay.audio add column wb_type  tinyint default 1 comment '1:白板 2：视频';
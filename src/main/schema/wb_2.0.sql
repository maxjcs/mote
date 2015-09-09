
#白板增加版本号控制

alter table education_biz.feud_detail_wb add column wb_version tinyint default 1 comment '版本号:1初始版本 2 2版本';


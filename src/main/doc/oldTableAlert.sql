	/***             teacher 表   ***/
	ALTER TABLE teacher ADD avatar_url varchar(200)  COMMENT '头像url';
	ALTER TABLE teacher ADD gender int  COMMENT '性别 1：男 2：女';
	ALTER TABLE teacher ADD star int  COMMENT '星级';
	ALTER TABLE teacher ADD teacher_identify int  COMMENT '教师身份 1:教师 2:学生';
	ALTER TABLE teacher ADD point int  COMMENT '积分';
	ALTER TABLE teacher ADD qrcode_url varchar(200)  COMMENT '二维码url';
	ALTER TABLE teacher ADD device_id varchar(100)  COMMENT '设备id';
     alter table teacher add column last_task_time  timestamp default current_timestamp;

	/***             audio_upload 表   ***/
	ALTER TABLE audio_upload ADD type  int COMMENT '1 audio,2 whiteboard';
	ALTER TABLE audio_upload ADD source int COMMENT 'task 1.每日任务,2.tall 微店,3.feud 抢答';
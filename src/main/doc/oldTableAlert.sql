	/***             teacher ��   ***/
	ALTER TABLE teacher ADD avatar_url varchar(200)  COMMENT 'ͷ��url';
	ALTER TABLE teacher ADD gender int  COMMENT '�Ա� 1���� 2��Ů';
	ALTER TABLE teacher ADD star int  COMMENT '�Ǽ�';
	ALTER TABLE teacher ADD teacher_identify int  COMMENT '��ʦ��� 1:��ʦ 2:ѧ��';
	ALTER TABLE teacher ADD point int  COMMENT '����';
	ALTER TABLE teacher ADD qrcode_url varchar(200)  COMMENT '��ά��url';
	ALTER TABLE teacher ADD device_id varchar(100)  COMMENT '�豸id';
     alter table teacher add column last_task_time  timestamp default current_timestamp;

	/***             audio_upload ��   ***/
	ALTER TABLE audio_upload ADD type  int COMMENT '1 audio,2 whiteboard';
	ALTER TABLE audio_upload ADD source int COMMENT 'task 1.ÿ������,2.tall ΢��,3.feud ����';
INSERT INTO `app_versions_info` (`id`, `version_code`, `version_name`, `need_update`, `force_update`, `description`, `app_type`, `add_time`, `download_url`)
VALUES
	(4, 1, '1.0', 'Y', 'Y', '安卓版本1.0', 1, '2015-04-11 14:40:06', 'http://www.baidu.com');
	

create index learnTalkIndex on learn_message(teacher_id,send_msg_user_type,msg_type,teacher_offer_id)	

alter table teacher modify name varchar(50) null;
alter table teacher modify id_number varchar(20) null;
alter table teacher modify qq varchar(20) null;
alter table teacher modify school_id bigint(20) null;
alter table teacher modify id_card_image_url varchar(200) null;
alter table teacher modify student_card_image_url varchar(200) null;
alter table teacher modify status tinyint(4) null;
alter table teacher modify province_id int(11) null;
alter table teacher modify city_id int(11) null;
alter table teacher modify weixin varchar(20) null;
alter table teacher modify subjects varchar(20) null;
alter table teacher modify grades varchar(20) null;
alter table teacher modify bank_card varchar(50) null;
alter table teacher modify bank varchar(100) null;
alter table teacher modify alipay varchar(100) null;
alter table teacher modify course_time varchar(100) null;
alter table teacher modify self_description text null;
alter table teacher modify course_year int(50) null;
alter table teacher modify course_area varchar(100) null;
alter table teacher modify mingshihui tinyint(4) null;
alter table teacher modify xingjihua tinyint(4) null;
alter table teacher modify xingjihua tinyint(4) null;
alter table teacher modify avatar_url varchar(200) null;
alter table teacher modify gender int(11) null;
alter table teacher modify star int(11) null;
alter table teacher modify teacher_identify int(11) null;
alter table teacher modify point int(11) null;
alter table teacher modify qrcode_url int(11) null;
alter table teacher modify device_id varchar(100) null;
alter table teacher modify device_id varchar(100) null;
alter table teacher modify last_task_time datetime null;
alter table teacher modify last_device_id varchar(100) null;
alter table teacher modify last_device_type varchar(100) null;
alter table teacher modify complete_new_user_task tinyint(4) null;
alter table teacher modify online_status varchar(1) null;
alter table teacher modify cur_org_id int(11) null;
alter table teacher modify capacity_test_status tinyint(4) null;
alter table teacher modify capacity_test_complete_time datetime null;
alter table teacher modify capacity_test_give_point int(11) null;
alter table teacher modify capacity_test_is_joined int(11) null;













	
	
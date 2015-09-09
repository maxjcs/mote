package com.xuexibao.teacher.util;

public class BusinessConstant {
    
	  //再次升星时间
    public static final int REAPPLY_RATTING_HOURS = 24;
    
    
    //验证码过期时间60秒
    public static final int VERIFY_CODE_EXPIRATION_TIME = 60;
    
    //验证码长度
    public static final int VERIFY_CODE_LENGTH = 6;
    
    //验证码信息
    public static final String VERIFY_CODE = "这是您的验证码：%s，请保管好，有效期60秒【学习宝】";
    
    //每次取题目的个数
    public static final int QUESTION_BATCH_LIMIT = 10;
    
    //新手任务个数
    public static final int NEW_TASK_BATCH_LIMIT = 10;
    
    //每个教师取客件数
    public static final int TEACHER_COURSE_WARE_LIMIT = 2;
    
    //销售分成比例默认值
    public static final int AUDIO_SALE_TEACHER_PERCENT = 50;
    
    //销售分成比例默认值
    public static final int AUDIO_SALE_STUDENT_PERCENT = 50;
    
    //每次取录制的个数
    public static final int AUDIO_BATCH_LIMIT = 10;
    
    //删除占用过期时间1天
    public static final int ALLOT_EXPIRED_TIME = 1;
    
    //每个用户获取题目间隔2分钟
    public static final int GET_QUESTION_INTERVAL = 120;
    
    //审核不通过重新录制的限制天数
    public static final int RE_RECORD_LIMIT_DAYS = 1;
    
    //system_config表中记录上次轮询的数据版本号的key_name
    public static final String STUDENT_REQEUST_VERSION_KEY = "STUDENT_REQEUST_VERSION_KEY";
    
    //每道紧急题目推送教师人数上线为10，假如每个人都推浪费资源
    public static final int EMERGENCY_QUESTION_PUSH_TEACHER_MAX_LIMIT = 10;
    
    //性能日志的最大时间 单位ms
    public static final int MAX_PERFORMLOG_WARN_TIME = 1000;
    
}

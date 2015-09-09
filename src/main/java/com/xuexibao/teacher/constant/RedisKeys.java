package com.xuexibao.teacher.constant;

public interface RedisKeys {
	//抢答队列KEY,需要加上feudQueueId
	public final static String feudQueueKey = "FQK:";
	//每个抢答者记时间Key  key+ teacherid  value 记录当前时间戳 long型
	public final static String feudTeacherTimeKey = "FT:";
	//每个feudQuestIdKey
	public final static String feudQuestIdKey = "FQI:";
		

}

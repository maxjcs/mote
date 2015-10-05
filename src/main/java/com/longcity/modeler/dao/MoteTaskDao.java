package com.longcity.modeler.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.longcity.manage.model.vo.MoteDetailVO;
import com.longcity.modeler.core.MybatisMapper;
import com.longcity.modeler.model.MoteTask;
import com.longcity.modeler.model.vo.MoteTaskVO;
import com.longcity.modeler.model.vo.TaskVO;

@MybatisMapper
@Repository
public interface MoteTaskDao {
	
    int deleteByPrimaryKey(Integer id);

    int insert(MoteTask record);

    MoteTask selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(MoteTask record);
    
    @SuppressWarnings("rawtypes")
	void addOrderNo(Map paramMap);
    
    void updateStatus(Integer id,Integer status);
    
	@SuppressWarnings("rawtypes")
	void selfBuy(Map paramMap);
	
	@SuppressWarnings("rawtypes")
	void returnItem(Map paramMap);
	
	@SuppressWarnings("rawtypes")
	void verifyReturnItem(Map paramMap);
	
	@SuppressWarnings("rawtypes")
	void finishShowPic(Map paramMap);
	
	@SuppressWarnings("rawtypes")
	void uploadImg(Map paramMap);
	
	@SuppressWarnings("rawtypes")
	List<TaskVO> stasticByTaskIds(Map paramMap2);
	
	@SuppressWarnings("rawtypes")
	List<MoteTaskVO> getMoteListByTaskId(Map paramMap2);
	
	Integer countMoteListByTaskId(Integer taskId);
	
	List<MoteTask> getAcceptList(Integer maxId);
	
	List<MoteTask> getReturnItemList(Integer maxId);
	
	@SuppressWarnings("rawtypes")
	List<MoteDetailVO> queryListByMoteId(Map paramMap2);
	
	Integer countByMoteId(Integer moteId);  
	
	Integer getMoteTaskNumBySellerId(Integer sellerId);
	
	Integer getMoteTaskNumByMoteId(Integer moteId);
	
	Integer getMoteTaskTotalFeeBySellerId(Integer sellerId);
	
	Integer getMoteTaskTotalFeeByMoteId(Integer moteId);
	
	MoteTaskVO getTop1Mote(); 
	
    int getTotalAcceptedNum(Integer taskId);
    
    int getMoteAcceptedNumDaily(Integer moteId);
    
    int getMoteTaskNumByStatus(Integer moteId,String status);
    
    List<Integer> get15DaysList(Integer moteId);
	
	List<Integer> getAcceptedTaskIdList(Integer moteId);
	
	Integer getUnFinishNumByMoteId(Integer moteId);
	
	MoteTask queryByMoteIdAndTaskId(Integer moteId,Integer taskId);
	
}
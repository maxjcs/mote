package com.xuexibao.teacher.dao;

import java.util.List;
import java.util.Map;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.FeudQuest;
import com.xuexibao.teacher.model.vo.FeudQuestDetailVO;
import com.xuexibao.teacher.model.vo.FeudTeacherIsFree;
import com.xuexibao.teacher.model.vo.WaitFeudListVO;
@MybatisMapper
public interface FeudQuestDao {
    int deleteByPrimaryKey(Long id);

    int insert(FeudQuest record);

    FeudQuest selectByPrimaryKey(Long id);
    List<WaitFeudListVO> getResumeFeudQuestById(Long id);

    List<FeudQuest> selectAll();

    int updateByPrimaryKey(FeudQuest record);
    List<FeudQuest> selectFQByStudentIdAndQuestRealId(FeudQuest record);
    @SuppressWarnings("rawtypes")
	List<WaitFeudListVO> getFeudQuestList(Map map);
    //普通待解答请求
    List<WaitFeudListVO> getCommonFeudQuestList(Map map);
    //只对老师自己定向请求
    List<WaitFeudListVO> getFeudQuestListSelf(Map map);
    
  
    List<FeudTeacherIsFree> getFeudTeachersIsFree(Map map);
    
    List<FeudQuest> hasAskTeacherWithQuestion(Map map);
    Integer getDirectFeudWithTeacher(Map map);
    
    
    
    
    List<WaitFeudListVO> getAllStatusFeudQuestListById(Map map);
    int getHomeFeudQuestListCount(Map map);
    List<WaitFeudListVO> getHomeFeudQuestList(Map map);
    
    
    @SuppressWarnings("rawtypes")
	List<FeudQuestDetailVO> getFeudQuestDetail(Long questRealId);
    int getFeudQuestCountForPageHome(String teacherId);
    List<FeudQuest> getExpireStudentQuest();
 
}
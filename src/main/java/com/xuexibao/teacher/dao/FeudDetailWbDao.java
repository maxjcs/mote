package com.xuexibao.teacher.dao;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.FeudDetailWb;
import java.util.List;
@MybatisMapper
public interface FeudDetailWbDao {
    int deleteByPrimaryKey(Long id);

    int insert(FeudDetailWb record);

    FeudDetailWb selectByPrimaryKey(Long id);

    List<FeudDetailWb> selectAll();
    List<FeudDetailWb>  selectAllByWbId(String wbId);
    int updateByPrimaryKey(FeudDetailWb record);
    
}
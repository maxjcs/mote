package com.xuexibao.teacher.dao;

import java.util.List;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.Wb2videoProcess;

@MybatisMapper
public interface Wb2videoProcessDao {
    int deleteByPrimaryKey(Long id);

    int insert(Wb2videoProcess record);

    Wb2videoProcess selectByPrimaryKey(Long id);

    List<Wb2videoProcess> selectAll();
    Wb2videoProcess selectByWbId(String wbId);

    int updateByPrimaryKey(Wb2videoProcess record);
}
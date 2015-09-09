package com.xuexibao.teacher.dao;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.FeudPointFeeConf;
import java.util.List;
@MybatisMapper
public interface FeudPointFeeConfDao {
    int deleteByPrimaryKey(Integer id);

    int insert(FeudPointFeeConf record);

    FeudPointFeeConf selectByPrimaryKey(Integer id);

    List<FeudPointFeeConf> selectAll();

    int updateByPrimaryKey(FeudPointFeeConf record);
}
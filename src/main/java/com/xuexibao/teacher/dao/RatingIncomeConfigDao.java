package com.xuexibao.teacher.dao;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.RatingIncomeConfig;
import java.util.List;
@MybatisMapper
public interface RatingIncomeConfigDao {

    RatingIncomeConfig selectByPrimaryKey(Integer id);

    List<RatingIncomeConfig> selectAll();

 
}
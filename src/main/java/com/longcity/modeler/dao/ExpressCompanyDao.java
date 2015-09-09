package com.longcity.modeler.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.longcity.modeler.model.ExpressCompany;
import com.xuexibao.teacher.core.MybatisMapper;

@MybatisMapper
@Repository
public interface ExpressCompanyDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ExpressCompany record);

    ExpressCompany selectByPrimaryKey(Integer id);

    List<ExpressCompany> selectAll();

    int updateByPrimaryKey(ExpressCompany record);
}
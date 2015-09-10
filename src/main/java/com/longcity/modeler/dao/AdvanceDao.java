package com.longcity.modeler.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.longcity.modeler.core.MybatisMapper;
import com.longcity.modeler.model.Advance;


@MybatisMapper
@Repository
public interface AdvanceDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Advance record);

    Advance selectByPrimaryKey(Integer id);

    List<Advance> selectAll();

    int updateByPrimaryKey(Advance record);
}
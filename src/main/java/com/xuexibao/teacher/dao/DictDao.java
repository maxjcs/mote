package com.xuexibao.teacher.dao;

import java.util.List;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.Dict;

@MybatisMapper
public interface DictDao {

	List<Dict> listDictByType(int type);
}

package com.longcity.manage.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.longcity.manage.model.param.QuerySellerParamVO;
import com.longcity.manage.model.vo.SellerVO;
import com.longcity.modeler.core.MybatisMapper;


@MybatisMapper
@Repository
public interface SellerDao {
	
     List<SellerVO>	querySellerList(QuerySellerParamVO vo);
     
     int countSellerList(QuerySellerParamVO vo);

}

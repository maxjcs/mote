package com.xuexibao.teacher.dao;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.PointOffline;
import java.util.List;

/**
 * 
 * @author oldlu
 * 
 */
@MybatisMapper
public interface PointOfflineDao {

	List<PointOffline> allPointOffline();

	void updatePointOffline(PointOffline pointoffline);
}

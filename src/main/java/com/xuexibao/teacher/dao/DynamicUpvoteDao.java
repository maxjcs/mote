package com.xuexibao.teacher.dao;
import java.util.List;
import java.util.Map;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.Dynamic;
import com.xuexibao.teacher.model.DynamicUpvote;

/**
 * 
 * @author oldlu
 *
 */
@MybatisMapper
public interface DynamicUpvoteDao {

	DynamicUpvote loadDynamicUpvote(DynamicUpvote upvote);

	List<DynamicUpvote> listDynamicUpvote(Map<String,Object> paramMap);

	void addDynamicUpvote(DynamicUpvote dynamicupvote);

	void deleteDynamicUpvote(DynamicUpvote dynamicupvote);

	List<DynamicUpvote> listUpVoteByIds(Map<String, Object> paramMap);

	List<Dynamic> groupCountUpvoteByDynamicIds(List<Long> noCacheIds);
}

package com.xuexibao.teacher.dao;
import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.Dynamic;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author oldlu
 *
 */
@MybatisMapper
public interface DynamicDao {

	Dynamic loadDynamic(Long id);

	List<Dynamic> listDynamic(Map<String, Object> paramMap);

	Integer countDynamic(Dynamic dynamic);

	void addDynamic(Dynamic dynamic);

	void updateDynamic(Dynamic dynamic);

	int isExistDynamic(Dynamic dynamic);

	boolean hasDynamicForAudioSet(String setId);

	void removeDynamic(Long dynamicId);

	List<Dynamic> listDynamicByIds(String[] dynamicIds);

	void restoreDynamic(Long dynamicId);

	Long getDynamicIdBySetId(String setId);
}

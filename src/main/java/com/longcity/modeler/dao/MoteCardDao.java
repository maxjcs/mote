/**
 * 
 */
package com.longcity.modeler.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.longcity.modeler.core.MybatisMapper;
import com.longcity.modeler.model.MoteCard;

/**
 * @author maxjcs
 *
 */
@MybatisMapper
@Repository
public interface MoteCardDao {
	
	int insert(MoteCard record);
	
	void delete(Integer id);
	
	List<MoteCard> queryByUserId(Integer userId);

}

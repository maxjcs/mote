package com.xuexibao.teacher.dao;
import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.Rating;
import java.util.List;

/**
 * 
 * @author oldlu
 *
 */
@MybatisMapper
public interface RatingDao {

	List<Rating> allRating();

	void updateRating(Rating rating);
}

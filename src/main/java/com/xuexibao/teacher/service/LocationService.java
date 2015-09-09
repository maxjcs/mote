package com.xuexibao.teacher.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.xuexibao.teacher.model.TeacherLocation;


/**
 * 教师经纬度坐标
 * @author maxjcs
 *
 */
@Service
public class LocationService {
	
	@Autowired
    MongoTemplate mongoTemplate;
	
	/**
	 * 保存老师的坐标
	 * @param teacherId
	 * @param longitude
	 * @param latitude
	 */
	public void save(String teacherId,Double longitude,Double latitude){
		TeacherLocation position=new TeacherLocation();
		position.set_id(teacherId);
		position.setPosition(new double[]{longitude,latitude});
		position.setUpdateTime(new Date());
		mongoTemplate.save(position);
	}
 
    public List<TeacherLocation> findCircleNear(Point point,double minDistance, double maxDistance,Integer count) {
        List<TeacherLocation> teacherLocations=mongoTemplate.find(
                new Query(Criteria.where("position").nearSphere(point).minDistance(minDistance/6371004).maxDistance(maxDistance/6371004)).limit(count),
                TeacherLocation.class);
        for(TeacherLocation teacherLocation:teacherLocations){
        	//计算距离，单位米
        	Double distance=getDistance(point.getX(),point.getY(),teacherLocation.getPosition()[0],teacherLocation.getPosition()[1]);
        	teacherLocation.setDistance(distance);
        }
        return teacherLocations;
    }
    
    /**
     * 查找附近老师的个数
     * @param point
     * @param maxDistance
     * @return
     */
    public Integer findCircleNearCount(Point point,double maxDistance) {
        List<TeacherLocation> teacherLocations=mongoTemplate.find(
                new Query(Criteria.where("position").nearSphere(point).maxDistance(maxDistance/6371004)),TeacherLocation.class);
        return teacherLocations.size();
    }
    
    public TeacherLocation findDocumentById(String teacherId) {
        TeacherLocation location = mongoTemplate.findOne(new Query(Criteria.where("_id").is(teacherId)),TeacherLocation.class);
        return location;
    }
    
	/**
	 * 计算地球上任意两点(经纬度)距离
	 * 
	 * @param long1
	 *            第一点经度
	 * @param lat1
	 *            第一点纬度
	 * @param long2
	 *            第二点经度
	 * @param lat2
	 *            第二点纬度
	 * @return 返回距离 单位：米
	 */
	public static double getDistance(double long1, double lat1, double long2,
			double lat2) {
		double a, b, R;
		R = 6371004; // 地球半径
		lat1 = lat1 * Math.PI / 180.0;
		lat2 = lat2 * Math.PI / 180.0;
		a = lat1 - lat2;
		b = (long1 - long2) * Math.PI / 180.0;
		double d;
		double sa2, sb2;
		sa2 = Math.sin(a / 2.0);
		sb2 = Math.sin(b / 2.0);
		d = 2
				* R
				* Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
						* Math.cos(lat2) * sb2 * sb2));
		return d;
	}
    
    
    
 
}

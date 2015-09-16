/**
 * 
 */
package com.longcity.manage.model.param;

import com.longcity.manage.model.vo.QueryBaseVO;
import com.longcity.modeler.model.vo.MoteTaskVO;

/**
 * 项目详情
 * @author maxjcs
 *
 */
public class QueryTaskDetailParamVO extends QueryBaseVO<MoteTaskVO>{
	
	Integer taskId;

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

}

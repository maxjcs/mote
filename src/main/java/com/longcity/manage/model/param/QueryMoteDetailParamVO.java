/**
 * 
 */
package com.longcity.manage.model.param;

import com.longcity.manage.model.vo.QueryBaseVO;
import com.longcity.modeler.model.vo.MoteTaskVO;

/**
 * @author maxjcs
 *
 */
public class QueryMoteDetailParamVO extends QueryBaseVO<MoteTaskVO>{
	
	Integer moteId;

	public Integer getMoteId() {
		return moteId;
	}

	public void setMoteId(Integer moteId) {
		this.moteId = moteId;
	}
	
}

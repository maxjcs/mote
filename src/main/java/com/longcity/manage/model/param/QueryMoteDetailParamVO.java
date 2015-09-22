/**
 * 
 */
package com.longcity.manage.model.param;

import com.longcity.manage.model.vo.MoteDetailVO;
import com.longcity.manage.model.vo.QueryBaseVO;

/**
 * @author maxjcs
 *
 */
public class QueryMoteDetailParamVO extends QueryBaseVO<MoteDetailVO>{
	
	Integer moteId;

	public Integer getMoteId() {
		return moteId;
	}

	public void setMoteId(Integer moteId) {
		this.moteId = moteId;
	}
	
}

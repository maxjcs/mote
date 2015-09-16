/**
 * 
 */
package com.longcity.manage.model.param;

import com.longcity.manage.model.vo.QueryBaseVO;
import com.longcity.modeler.model.Task;

/**
 * 商家详情
 * @author maxjcs
 *
 */
public class QuerySellerDetailParamVO extends QueryBaseVO<Task>{
	
	Integer sellerId;

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	
}

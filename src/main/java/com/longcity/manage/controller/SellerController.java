/**
 * 
 */
package com.longcity.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.longcity.manage.model.param.QuerySellerParamVO;

/**
 * @author maxjcs
 *
 */
@Controller
@RequestMapping("seller")
public class SellerController extends BaseController{
	
    @RequestMapping(value = "list")
    protected String manage(QuerySellerParamVO paramVO, ModelMap resultMap) {
    	resultMap.put("aa", "ccccc");
        return "seller/list";
    }

}

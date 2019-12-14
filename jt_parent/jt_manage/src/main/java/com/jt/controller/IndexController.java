package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
	
	/**
	 * 不是正版rest风格
	 * 使用：1、参数使用"/"分割
	 * 		2、参数使用花括号包裹，并且设置变量名称
	 * 		3、使用注解，@PathVariable 标识参数，要求参数名称需要一致
	 * 真正的rest风格的url，是url不变，访问方式改变（get、post、、、、）
	 * 补充：
	 * @param moduleName
	 * @return
	 */
	@RequestMapping("/page/{moduleName}")
	public String module(@PathVariable String moduleName) {
		
		return moduleName;
	}


}

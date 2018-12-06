package com.bjhy.robote.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * 上传主页
 * @author chenhao
 *
 * 2018年5月17日
 */
@Controller
public class IndexController {
	public static final String INDEX = "index";

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index() {
		return INDEX;
	}

}

package com.bjhy.robote.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bjhy.robote.service.HttpClientService;


/**
 * 
 * @author chenhao
 *
 * 2018年5月16日
 */
@RestController
public class TestPostController {
	
	public static final String JSESSIONID="1jwwt6nnk8hj41s46tjy5bd61x";
	//api url地址
	public static final String URL="http://192.168.0.94:7778/code-robot/property";
	
    @Autowired
    HttpClientService httpClientService;
    
    @RequestMapping("/doPost")
    public String doPost(){
        
        //post请求
        // 封装参数，千万不要替换为Map与HashMap，否则参数无法传递
        MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
        
        params.add("id", "");
        params.add("name", "测试POST");
        params.add("fieldName", "fieldName");
        params.add("dataType", "String");
        params.add("inputType", "text");
        params.add("inputValue", "");
        params.add("pxNum", "5");
        params.add("domain.id", "8dc347a98f624ffbbb44b582ee2577c5");
        
        //发送http请求并返回结果
        return httpClientService.doPost(URL,params,JSESSIONID);
    }
}
package com.memo.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
	@ResponseBody
	@GetMapping("/test1")
	public String test1() {
		return "Hello world!";
	}
	
	@ResponseBody
	@RequestMapping("/test2")
	public Map<String, Object> test2() {
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("name", "홍길동");
		map.put("nickName", "kdhong");
		map.put("email", "kdhonfg@gmail.com");
		
		return map;
	}
	
	//@ResponseBody
    @RequestMapping("/test3")
    public String test3() {  
        return "test/test"; 
    }
    
    //DatabaseConfid.java 생성
    // DB아보는 설정 제거 (snsApplication.java)
    // resources/mappers.xml 설정
    // application.yml DB설정 추가
    // logback 설정
//    @Autowired
//    private PostMapper postMapper;
//    
//    @ResponseBody
//    @RequestMapping("/test4")
//    public List<Map<String,Object>> test4() {
//    	
//    	return postMapper.selectPostList(); 
//    }
//    
    
}
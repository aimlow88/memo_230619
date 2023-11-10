package com.memo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.memo.common.FileManagerService;
import com.memo.interceptor.PermissionInterceptor;

@Configuration    // 설정을 위한 spring bean
public class WebMvcConfig implements WebMvcConfigurer{
	
	@Autowired
	private PermissionInterceptor interceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
		.addInterceptor(interceptor)
		.addPathPatterns("/**")
		.excludePathPatterns("/error", "/static/**", "/user/sign-out")
		;
		
	}
	// 웹 이미지 path와 서버에 업로드된 이미지와 맵핑 설정
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
		.addResourceHandler("/images/**")  // 웹 이미지 path   http://localhost/images/loginId+현재시간/sun.png
		.addResourceLocations("file:///" + FileManagerService.FILE_UPLOAD_PATH);  // 실제 파일 위치
	}
	

}

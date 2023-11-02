package com.memo.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.memo.common.EncryptUtils;
import com.memo.user.bo.UserBO;
import com.memo.user.entity.UserEntity;

@RequestMapping("/user")
@RestController
public class UserRestController {
	
	
	@Autowired
	private UserBO userBO;
	
	
	/**
	 * 
	 * @param loginId
	 * @return
	 */
	@GetMapping("/check-duplicated-id")
	public Map<String, Object> isDuplicated(
			@RequestParam("loginId") String loginId
			) {
		
		UserEntity userEntity = userBO.getuserEntityByloginId(loginId);
		
		Map<String,Object> result = new HashMap<>();
		result.put("code", 200);
		
		if (userEntity == null ) {
			result.put("isDuplicated", false);
		} else {
			result.put("isDuplicated", true);
		}
		
		return result;
	}
	
	/**
	 * 회원가입 API
	 * @param loginId
	 * @param password
	 * @param name
	 * @param email
	 * @return
	 */
	@PostMapping("/sign-up")
	public Map<String, Object> signUp(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("email") String email
			){
		
		// password 해싱 - md5 알고리즘
		String hashedpassword = EncryptUtils.md5(password);
		
		// DB insert
		Integer id = userBO.addUser(loginId, hashedpassword, name, email);
		
		// 응답값
		Map<String, Object> result = new HashMap<>();
		if (id == null){
			result.put("code", 500);
			result.put("errorMessage", "회원가입 실패");
		} else {
			result.put("code", 200);
			result.put("result", "성공");
		}
		return result;
	}
	
	
	/**
	 * 로그인 API
	 * @param loginId
	 * @param password
	 * @param request
	 * @return
	 */
	@PostMapping("/sign-in")
	public Map<String, Object> signIn(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			HttpServletRequest request
			){
		
		// 비밀번호 hashing
		String encryptPassword = EncryptUtils.md5(password);
		
		UserEntity userEntity = userBO.getUserEntityByLoginIdAndPassword(loginId, encryptPassword);
//		UserEntity userEntity = userBO.getuserEntityByloginId(loginId);
		
		//응답값
		Map<String, Object> result = new HashMap<>();
		
		if (userEntity != null) {
			HttpSession session = request.getSession();
			session.setAttribute("userId", userEntity.getId());
			session.setAttribute("userLoginId", userEntity.getLoginId());
			session.setAttribute("userName", userEntity.getName());
			
			result.put("code", 200);
			result.put("result", "성공");
		} else {
			result.put("code", 500);
			result.put("errormessage", "존재하지 않는 사용자입니다.");
		}
		
		result.put("code", 200);
		result.put("result", "성공");
		
//		if (userEntity == null) {
//			result.put("idCheck", false);
//		} else if (userEntity.getPassword().equals(encryptPassword)) {
//			result.put("passwordCheck", true);
//		} else {
//			result.put("passwordCheck", false);
//		}
		
		return result;
	}

}

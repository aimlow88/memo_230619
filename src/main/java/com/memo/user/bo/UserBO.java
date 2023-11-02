package com.memo.user.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memo.user.entity.UserEntity;
import com.memo.user.repository.UserRepository;

@Service
public class UserBO {
	
	@Autowired
	private UserRepository userRepository;
	
	public UserEntity getuserEntityByloginId(String loginId) {
		return userRepository.findByLoginId(loginId);
	}
	
	public UserEntity getUserEntityByLoginIdAndPassword(String loginId, String password) {
		
		return userRepository.findByLoginIdAndPassword(loginId, password) ;
		
	}
	
	// input:4개 파라미터     output : id(pk)
	public Integer addUser(String loginId, String password, String name, String email) {
		
		UserEntity userEntity = userRepository.save(
				UserEntity.builder()
				.loginId(loginId)
				.password(password)
				.name(name)
				.email(email)
				.build()
				);
		
		return userEntity == null ? null : userEntity.getId();
		
	}

}

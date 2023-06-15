package com.cos.photogramstart.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional //Write(insert,Update,Delete)
	public User 회원가입(User user) {
		// 회원가입 진행
		String rawpassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawpassword);
		// 암호화된 패스워드가 담기게 된다.
		user.setPassword(encPassword);
		user.setRole("ROLE_USER");
		User userEntity =  userRepository.save(user);
		// save는 저장한 데이터를 리턴한다
		return userEntity;
	} 
	
}

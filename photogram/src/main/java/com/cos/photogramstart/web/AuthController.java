package com.cos.photogramstart.web;
 
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final에 대한 모든 생성자를 만들어준다.(final을 DI할때 사용)
@Controller // 1. IoC 2. 파일을 리턴하는 컨트롤러
public class AuthController { 

	
	private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	private final AuthService authService;
	
//	public AuthController(AuthService authService) {
//		this.authService = authService;
//	}
	
	@GetMapping("/auth/signin")
	public String signinForm() {
		return "auth/signin";
	}

	@GetMapping("/auth/signup")
	public String signupForm() {
		return "auth/signup";
	}
	
	// 회원가입 버튼 -> /auth/signup -> /auth/signin
	@PostMapping("/auth/signup")
	// ResponsBody가 붙어있다면 상단에 @Controller이라도 데이터를 반환한다	
									// BindingResult를 매개변수에 추가하여 잘못된게있는지 확인
	public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) { // key=vaule (x-www-form-urlencoded)
			// User < - SinupDto
			User user = signupDto.toEntity();
			authService.회원가입(user);
			return "auth/signin";	
	}	

}

package com.example.demo.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.User;


@RestController
public class HttpResponseJsonController {

	
	@GetMapping("/resp/json")
	public String respJson() {
		return "{\"username\":\"cos\"}";
	}
	
	@GetMapping("/resp/json/object")
	public User respJsonObject() {
		User user = new  User();
		user.setUsername("홍길동");
		return user;// 1. MessageConverter가 자동으로JavaObject를 Json으로 변경해서 통신을 통해 응답을해준다.
	}				// 2. @RestController 일때만 MessageConverter가 작동한다.
}

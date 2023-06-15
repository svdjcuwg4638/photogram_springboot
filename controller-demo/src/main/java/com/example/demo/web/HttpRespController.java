package com.example.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 파일을 리턴하기위해 controller로 
public class HttpRespController {

	@GetMapping("/txt")
	public String txt() {
		return "a.txt";// 프레임워크 사용(틀이 이미 정해져있음) - 일반 정적파일들은 resources/static 폴더 내부가 디폴트 경로이다.
	}
	
	@GetMapping("/mus")
	public String mus() {
		return"b"; 	//  mustache 템플릿 엔진 라이블러리 등록 완료 - temlplates 폴더안에 
					// .mustache을 나두면 확장자 없이 파일명만 적으면 자동으로 찾아감
	}
	
	@GetMapping("/jsp")
	public String jsp() {
		return"c"; // jsp엔진 사용 : src/main/webapp 폴더가 디폴트 경로!
					// /WEB-INF/views/c.jsp(ViewResolver)
	}
}

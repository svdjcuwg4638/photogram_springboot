package com.cos.photogramstart.handler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

@RestController // 데이터를 보낼거라서 restcontroller로 설정
@ControllerAdvice
public class ControllerExceptionHandler {

//	// CustomValidationException을 사용할거라고 명시
//	@ExceptionHandler(CustomValidationException.class)
//	// ? 라고 지정해놓으면 리턴 타입에 맞춰 자료형을 알맞게 기입해준다
//	public CMRespDto<?> validationException(CustomValidationException e) {
//		// 매개변수로 받아온 내가만든 에러의 map을받아와 반환시킨다.
//		
//		return new CMRespDto<Map<String,String>>(-1, e.getMessage(),e.getErrorMap());
//	}
	
	@ExceptionHandler(CustomValidationException.class)
	public String validationException(CustomValidationException e) {
		
		// 오류에 대한 Map내용을 String화하여 String반환
		// CMRespDto, Script비교
//		 1. 클라이언트에게 응답할 대는 Script 좋음.
//		 2. Ajax통신 - CMRespDto
//		 3. Android통신 - CMRespDto
		if(e.getErrorMap() == null) {
			return Script.back(e.getMessage());
		}else {	
			return Script.back(e.getErrorMap().toString());
		}
	}
	
	@ExceptionHandler(CustomException.class)
	public String exception(CustomException e) {
		return Script.back(e.getMessage());
	}
	
	
	@ExceptionHandler(CustomValidationApiException.class)
	public ResponseEntity<?> validationApiException(CustomValidationApiException e) {
		return new ResponseEntity<>(new CMRespDto<>(-1,e.getMessage(),e.getErrorMap()),
				HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CustomApiException.class)
	public ResponseEntity<?> apiException(CustomApiException e) {
		return new ResponseEntity<>(new CMRespDto<>(-1,e.getMessage(),null),
				HttpStatus.BAD_REQUEST);
	}
	
	
	
}

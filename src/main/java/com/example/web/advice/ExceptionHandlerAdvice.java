package com.example.web.advice;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*
 * @ControllerAdvice
 * 		- 여러 컨트롤러 클래스에서 공틍으로 사용되는 기능이 정의된 클래스임을 나타내는 어노테이션이다.
 * 		- 컨트롤러에서 사용된 공통기능이 구현된 메소드에는 아래의 어노테이션 중 하나가 봍는다. 
 * 		  @ExceptionHandler
 * 				+ 예외처리를 담당하는 메소드임을 나타내는 어노테이션이다. 
 * 		  @InitBinder
 * 				+ 컨트롤러 전달되는 요청 파라미터에 대한 추가적인 작업을 수행하는 메소드임을 나타내는 어노테이션이다.
 * 		  @ModelAttribute
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {
// 더구체적인 exception에 매핑된다. (더가깝다)	
	@ExceptionHandler(DataAccessException.class)
	public String dataAccessExceptionHandle() {
		return "error/db";
	}

	@ExceptionHandler(Exception.class)
	// exception예외가 발생시 실행된다.
	public String exceptionHandle() {
		return "error/server";
	}

}

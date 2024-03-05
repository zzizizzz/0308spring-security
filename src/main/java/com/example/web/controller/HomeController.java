package com.example.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.web.form.UserRegisterForm;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
/*
 * @Log4j2
 * 		*로그출력을 지원하는어노테이션이다.
 * 		*클래스에 아래의 코드를 추가한다.
 * 			private static Logger log = LogManager.getLogger(HomeController.class);
 * 		*요청핸들러 메소드에서의 아래의 메소드를 이용해서 로그를 출력할수 있다.
 * 			log.debug(메세지);
 * 			log.info(메세지);
 * 			log.error(메세지);
 * 		-System.out.prinln()대신 반드시 log를 사용하자
 * 		사용하지 않은 변수도 없어야하고
 * 		사용하지 않은 임포트도 없어야하고
 * 		
 * 
 */
@Controller
@Log4j2
public class HomeController {
		
	//	@RequestMapping("/") - > get방식 post방식 다 가능하다.
	@RequestMapping("/")
	public String home() {
		return "home";
	}
	
	//레지스터폼
	@GetMapping("/register")
	public String form(Model model) {
		model.addAttribute("userRegisterForm", new UserRegisterForm());
		return "form";
	}
	
	
	@PostMapping("/register")
	public String register(@Valid UserRegisterForm form, BindingResult errors) {
		// @valid 유효성 체크를 해준다.  / BindingResult검사 결과가 들어있다.
		
		// 폼입력값 유효성 체크를 통과하지 못한 경우, 회원가입 화면(form)으로 내부이동
		if (errors.hasErrors()) {
			return "form";
		}
		return "redirect:/";
	}

}

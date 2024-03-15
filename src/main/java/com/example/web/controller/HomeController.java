package com.example.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.exception.AlreadyUsedEmailException;
import com.example.exception.AlreadyUsedIdException;
import com.example.service.UserService;
import com.example.vo.Company;
import com.example.vo.User;
import com.example.web.form.UserRegisterForm;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class HomeController {
	
	private final UserService userService;
	
	@GetMapping("/app1")
	@ResponseBody
	public String app1() {
//		return "안녕하세요 나는 데이터입니다.";
		// jsp로 내부이동하지 않고 바디에 담겨서 표현하게된다.
		return "텍스트입니다";
	}
	@GetMapping("/app2")
	@ResponseBody
	public String[] app2() {
		String[] userNames = {"홍길동", "이순신", "김유신"};
		return userNames;
	}
	
	@GetMapping("/app3")
	@ResponseBody
	public Company app3() {
		Company c = new Company();
		c.setNo(100);
		c.setName("삼성전자");
		c.setTel("02-1111-1111");
		c.setZipcode("123456");
		c.setAddress1("서울시 종로규");
		c.setAddress2("보익동 빌딩");
		
		return c;
	}
	@GetMapping("/app4")
	@ResponseBody
	public List<Company> app4() {
		Company c1 = new Company();
		c1.setNo(100);
		c1.setName("삼성전자");
		c1.setTel("02-1111-1111");
		
		Company c2 = new Company();
		c2.setNo(35);
		c2.setName("대우");
		c2.setTel("02-123456-456");
		
		return List.of(c1, c2);
	}
	
	//	@RequestMapping("/") - > get방식 post방식 다 가능하다.
	@RequestMapping("/")
	public String home() {
		return "home";
	}
	//누구나
	//레지스터폼
	@GetMapping("/register")
	public String form(Model model) {
		model.addAttribute("userRegisterForm", new UserRegisterForm());
		return "form";
	}
	
	//누구나
	@PostMapping("/register")
	public String register(@Valid UserRegisterForm form, BindingResult errors, RedirectAttributes redirectAttributes) {
		// @valid 유효성 체크를 해준다.  // BindingResult검사 결과가 들어있다.
		
		
		// 폼입력값 유효성 체크를 통과하지 못한 경우, 회원가입 화면(form)으로 내부이동
		if (errors.hasErrors()) {
			return "form";
		}
		try {
			User user = userService.registerUser(form);
			redirectAttributes.addFlashAttribute("user", user);
		
			return "redirect:/completed";
		// 폼 입력값 유효성 체크를 통과한 경우
		}catch (AlreadyUsedIdException ex) {
			//이미 사용중인 아이디인 경우, 유효성 체크를 통과하지 못한 것으로 간주한다.
			// rejectValue(필드명, 에러코드, 에레메시지) 메소드는 BindingResult객체에   FieldError를 추가한다.
			// 입려폼 화면으로 내부이동시킨다.
			errors.rejectValue("id", null, ex.getMessage());
			// binding 에 새로운 에러를 추가하는것이다.
			return "form";
		}catch (AlreadyUsedEmailException ex) {
			errors.rejectValue("email", null, ex.getMessage());
			return "form";
		}
		
	}
		//요청 url
		@GetMapping("/completed")
		public String completed() {
			return "completed";
		}
	
		/*
		 *  요청방식
		 *  	GET
		 *  요청 URL
		 *  	localhost/login
		 *  요청내용
		 *  	로그인폼 화면을 요청한다.
		 *  처리내용
		 *  	뷰페이지(로그인 폼 화면, loginform.jsp) 이름을 반환한다.
		 */	
		@GetMapping("/login")
		public String loginform() {
			return "loginform";
		}
		@GetMapping("/accessdenied")
		public String accessDenied() {
			return "error/denied";
		}
}

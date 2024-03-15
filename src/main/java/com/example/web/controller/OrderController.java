package com.example.web.controller;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.service.OrderService;
import com.example.service.ProductService;
import com.example.vo.Order;
import com.example.vo.Product;
import com.example.web.dto.OrderDetailDto;
import com.example.web.form.OrderForm;

import lombok.RequiredArgsConstructor;
/*
 * @SessionAttributes와 @ModelAttribute
 * 		+ @SessionAttributes
 * 			- Model객체에 저장되는 데이터를 HttpSession에 저장시키는 어노테이션이다.
 * 			- 컨트롤러 클레스에 @SessionAttributes 어노테이션이 있으면
 * 			  해당 컨트롤러의 요청핸들러 메소드에서Model객체에 저장되는 데이터를 HttpSession 객체의 속성으로 저장시켜서
 * 			  해당 요청이 완료된후에 Model객체에 저장한 데이터가 유지되도록한다.
 * 			- 다른 요청핸들러 메소드에서 Model객체에 저장된 데이터를 공유하기 위해서다.
 * 			- Model 객체에 저장되는 모든 데이터가 HttpSession에 저장되는 것이 아니라, @SessionAtttributes 어노테이션에서
 * 			  지정한 이름과 동일한 이름으로 Model객체에 저장되는 데이터만 대상이 된다. (ex orderForm)
 * 
 * 		+ @ModelAttribute
 * 			- 요청핸들러 메소드의 매개뱐수에 적용했을 때
 * 		 	- 예시) public String sample(@ModelAttribute("sampleForm") sampleForm form) {....}
 * 					1. @ModelAttribute 어노테이션이 지정된 객체를 자동으로 생성한다.
 * 						만약, "sampleForm"이라는 이름으로 HttpSession에 저장된 객체가 있으면 그 객체를 가져온다.(새로만들지 않고 가져온다.)
 * 					2. 요청객체의 요청파라미터 값을 분석해서 SampleForm객체에 바인딩(넣는다)한다.
 * 					3. @ModelAttribute 어노테이션이 지정된 객체는 Model객체에 "sampleForm"이라는 이름으로 저장하고, 뷰페이지에 값을 출력할 수 있다.
 * 			- 메소드에 적용했을 떄
 * 			- 예시) @ModelAttribute("categories")
 * 					public List<Category> categories(){
 * 						List<Category> categories = categoryService.getAllCategories();
 * 						return categories;
 * 				}
 * 					1. 요청핸들러 메소드가 실행되기 전에 @ModelAttribute가 지정된 메소드 부터 먼저 실행된다.
 * 					2. @ModelAttribute 어노테이션이 부착된 메소드가 반환하는 값이 자동으로 Model객체에 저장된다.
 * 					3. 요청핸들러 메소드가 실행된다. 		
 * 					4. 여러 JSP에서 공통으로 사용하는 데이터를 @ModelAttribute 어노테이션을 적용한 메소드로 Model객체에 저장시키면
 * 					   각각의 요청핸들러 메소드에서 그 데이터를 Model객체에 저장시키는 작업을 수행할 필요가 없다.
 */

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
@SessionAttributes({"orderForm"})
@PreAuthorize("isAuthenticated()")  // 오늘추가(인증) 
//@SessionAttributes({"orderForm"}) 여러단계를 거쳐야하는경우orderForm이라는 속성명을 적는것이다.

public class OrderController {
	
	private final ProductService productService;
	private final OrderService orderService; //주입받는거

	/*
	 * 요청방식
	 * 	GET
	 * 요청 URL
	 * 	localhost/order/step1?no=190
	 * 요청파리미터
	 *  no=190
	 * 요청내용
	 * 	지정된 상품번호에 대한 주문정보 입력폼을 요청한다.
	 * 처리내용
	 * 	요청파라미터로 전달받은 상품정보에 대한 상세정보를 조회해서 모델에 저장하고, 주문정보 입력화면으로 내부이동한다.
	 */
	@GetMapping("/step1")
	public String step1 (@RequestParam("no")int productNo, Model model) {
		Product product = productService.getProduct(productNo); //상품정보조회
		// 주문폼화면에 출력한 상품정보를 조회하고, Model에 저장한다.
		model.addAttribute("product", product); // 출력을 위해서 모델에담는거
		
		// 여러 단계로 구분된 입력작업에서 입렵되는 데이터를 저장할 OrderForm 객체를 생성하고Model에 저장한다.
		// "orderForm"이라는 이름으로 Model객체에 저장되는 데이터는 @SessionAttributes("orderForm") 설정때문에 
		// OrderForm객체가 HttpSession에 자동으로 저장된다.
		model.addAttribute("orderForm", new OrderForm());		
		
		return "order/orderform";
	}
	/*
	 * 요청방식
	 * 		POST
	 * 요청 URL
	 * 		localhost/order/step2
	 * 요청 파라미터
	 * 		productNo=190&name = 갤럭시15&price=15000000 &amount=1 &totalPrice=15000000 order폼에서 확인
	 * 요청내용
	 * 		1단계 입력폼에서 입력한 내용을 전달하고, 2단계 입력폼을 요청한다.
	 * 처리내용
	 * 		1. @ModelAttribute("orderForm")어노테이션 HttpSession에서 "orderForm"이름으로 지정된 OrderForm객체를 가져온다,
	 * 		2. 요청파라미터로 전달된 갑을 분석해서 OrderForm객체에 저장한다.
	 * 		3. 요청핸들러 메소드의 매개변수 orderForm에 OrderForm객체를 전달한다.
	 * 		4. 2단계 입력폼에 표시할 정보를 조회하고, Model에 저장한다.
	 * 		5. 2단계 입력폼의 뷰페이지 이름을 반환한다.
	 * 		할일은 4, 5하고 1,2,3은 스프링이 한다. ("orderForm")없으면 새로만든다.
	 */
	@PostMapping("/step2")
	//OrderForm 담긴다.
	public String step2 (@ModelAttribute("orderForm") OrderForm orderForm) {
		// 2단계 입력폼에서 필요한 정보를 조회하고, Model에 저장하는 작업을 생략한다.
		
		return "order/payform";
	}
	/*
	 * 요청 방식
	 * 		POST
	 * 요청 URL
	 * 		localhost/order/step3
	 * 요청 파라미터
	 * 		payType=card
	 * 		&cardno = 123-456-789-456
	 * 		&months = 3
	 * 		&payAmount= 1500000
	 * 요청 내용
	 * 		2단계 입력폼에서 입력한 결재정보를 서버에 제출하고, 주문을 요청한다.
	 * 처리내용
	 * 		1. @ModelAttribute("orderForm")어노테이션 HttpSession에서 "orderForm"이름으로 지정된 OrderForm객체를 가져온다,
	 * 		2. 요청파라미터로 전달된 값을 분석해서OrderForm객체에 저장한다(결재정보가 OrderForm객체에 저장된다.)
	 * 		3. 요청핸들러 메소드의 매개변수로 OrderForm객체를 전달한다.
	 * 		4. OrederService 객체의 order(OrderForm orderForm)를 실행해서 주문관련 업무로직을 수행한다.
	 * 		5. 주문정보를 확인할수 있는 URL를 재요청하 URL로 보낸다.
	 * 
	 * 	SessionStatus
	 * 		+ HttpSession에 속성으로 추가된 객체를 삭제하는 기능을 제공한다.
	 * 		+ SessionAttributes 어노테이션으로 HttpSession에 저장된 데이터를 삭제한다.
	 * 	RedirectAttributes
	 * 		+ 요청핸들러 메소드 실행 후 리다이렉트 재요청하게 되는 다음 요청핸들러 메소드에 전달할 정보를 저장할 수 있는객체다.
	 * 		+ 요청핸들러 메소드에서 저장/변경/삭제 작업을 수행한 후에는 재요청URL을 응답으로 보내게 되는 이 경우
	 * 		  응답이 완료됨과 동시에 요청객체와 응답객체가 사라지기 때문에 Model에 데이터를 담아 놓으면 전부 사라진다.
	 * 		  일반적으로 재요청URL을 응답으로 보내는 요청핸들러 메소드서는 뷰에 데이터를 전달할 방법이 없다.
	 *  	+ 주요 메소드
	 *  	- RedirectAttributes addAttribute(String name, Object value)
	 *  			+ 요청 핸들러 메소드가 반환하는 재요청 URL에 쿼리 스트링으로 추가 될 값을 저장한다.
	 *  		예시)
	 *  			public String sample1(Form form, RedirectAttributes redirectAttributes){
	 *  				----------------------------------------------------------------
	 *  				RedirectAttribute addFlashAttribute("page", 1);
	 *  				RedirectAttribute addFlashAttribute("sort", "date");
	 *  				RedirectAttribute addFlashAttribute("rows", 10);
	 *  				return "redirect:list/{page}";
	 *  				return "redirect:list/{page}/${sort}/${rows}";
	 *  				값이 채워지고 page의 값은 들어가고 나머지는 쿼리스링으로 들어간다.
	 * 			}
	 * 			* 재요청 URL : list/1?sort=date&rows=10
	 * 			*{속성명} 표현식은 해당 위치에 RedirectAttribute에 추가한 속성 값이 표시되고, 나머지는 쿼스르트링 형태로 추가된다.
	 *  	-RedirectAttribute addFlashAttribute(String name, Object value)
	 *  		+ 이 메소드에 추가된 정보는 재요청 URL로 새로 요청한 요청핸들러 메소드의 뷰에 값을 전달할 수 있다.
	 *  		+ 이 메소드에 추가된 정보는 일시적으로 HttpSession객체에 속성으로 저장된다.
	 *  		+ 재요청 Url로 새로 요청한 요청핸들러 메소드의 Model로 값이 추가되고, 세션에서는 삭제된다.
	 */
	@PostMapping("/step3")
	public String step3(@ModelAttribute("orderForm") OrderForm orderForm, Principal principal, SessionStatus sessionStatus, RedirectAttributes redirectAttributes) {
		
		// 주문관련 업무로직 메소드를 실행한다.
		String userId = principal.getName();
		Order order = orderService.saveOrder(orderForm, userId);
		
		sessionStatus.setComplete(); //세션에 저장되어 있는것을 싹다 지우는것 이컨트롤러에서 세션어트리뷰트로 담은것만 지운다
			//ex 로그인 한것은지우지 않는다. 3단계에서 주문이완료되고 SessionStatus를 실행하면 sessionattribute를 지울수있다.
		
		redirectAttributes.addAttribute("orderNo", order.getNo());
//		redirectAttributes.addAttribute("aaa",1);
//		redirectAttributes.addAttribute("bbb",2);
//		redirectAttributes.addAttribute("ccc",3);
		// redirectAttributes - > 
		return "redirect:completed";
	}
	
	@GetMapping("/completed")
	public String completed(int orderNo,Principal principal, Model model) {
		
		String userId = principal.getName();
		OrderDetailDto dto = orderService.getOrderDeatail(orderNo, userId);
		model.addAttribute("dto", dto);
		
		return "order/completed";
	}
	/*
	 * @PathVariable (orderNo)와 매핑
	 * 요청방식
	 * 		GET
	 * 요청 URL
	 * 		localhost/order/detail/100018
	 * 요청 파라미터
	 * 		없음
	 * 		대신, 요청 경로에 주문번호가 포함되어 있다.
	 */
	@GetMapping("/detail/{orderNo}")
	public String detail(@PathVariable("orderNo")int orderNo,Principal principal, Model model) {
		String userId = principal.getName();
		OrderDetailDto dto = orderService.getOrderDeatail(orderNo, userId);
		model.addAttribute("dto", dto);
		
		return "order/detail";
	}
//	
}

package com.example.web.advice;

import java.util.List;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.mapper.ProductCategoryMapper;
import com.example.service.ProductService;
import com.example.vo.Company;
import com.example.vo.ProductCategory;

import lombok.RequiredArgsConstructor;

/*
 * @ModelAttribute
 * 		+ Model객체의 속성 관련 어노테이션이다.
 * 		+ 메소드와 매개변수에 지정 할 수 있다.
 * 		+ 메소드에 @ModelAttribute 어노테이션을 지정하면 그 메소드 반환하는 값을 Model 객체 저장시킨다. 
 * 		+ spring mvc는 요청핸들러 메소드를 실행하기 전에 @ModelAttribute 어노테이션이 지정된 메소드를 먼저 실행해서
 * 		  Model객체에 값을 저장한다.
 * 		+ 따라서, 많은 요청 핸들러 메소드가 실행된 다음에 내부이동하는 JSP에서 특정한 값을 표현하는 부분이 있으면,
 * 		  각각의 요청핸들러 메소드에서 그 정보를 조회해서 Model객체에 저장하지 말고,
 * 		  @ModelAttribute 어노테이션이 지정된 메소드에서 정보를 조회하고, 반복하도록 하자. 그러면 모델에 담긴다.
 */

@ControllerAdvice
@RequiredArgsConstructor
public class ModelAttributeAdvice {
	
	private final ProductService productService;

	@ModelAttribute(name = "productCategories")
	//@ModelAttribute 담은이름이 홈에 나오도록.
	public List<ProductCategory> productCategories() {
		return productService.getAllProductCategories();
		
	}
	
	@ModelAttribute(name="companies")
	public List<Company> companies(){
		return productService.getAllCompanies();
	}
}

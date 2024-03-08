package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
/*
 * @Configuration
 * 		- 이 클래스는 스프링 환경설정 파일 역할을 하는 클래스임을 나타내는 어노테이션이다.
 * 		- 이 클래스를 스프링의 XML 파일을 대신한다.
 * 		- @Bean 어노테이션으로 스프링 컨테이너에 등록할 객체를 반환하는 메소드를 정의하고, 그 메소드가 반환하는 객체가 스프링 컨테이너의 빈으로 등록된다.
 * 
 * @EnableWebSecurity
 * 		- 
 * 
 * @Bean
 * 		- 스프링 컨테이너의 빈으로 객체를 등록시키는 어노테이션이다.
 * 		- 이 어노테이션이 부착된 메소드가 반환하는 객체는 스프링 컨테이너의 빈으로 자동 등록된다.
 * 		- 사용 예시
 * 			1. 단독으로 생성가능한 객체를 생성시켜서 스프링 컨테이너의 빈으로 등록하기
 * 				XML
 * 					<bean id="passwordEncoder" class="org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder">
 * 					</bean>
 * 						java
 * 							@Bean
 * 							public PasswordEncoder passwordEncoder(){
 * 								return new BCryptPasswordEncoder();
 * 
 * 			2.  객체 생성할 때 다른객체를 주입받아야 되는 객체를 생성시켜서 스프링 컨테이너의 빈으로 등록하기
 * 				XML쓰던것(예전)
 * 					<bean id="userService" class="com.example.service.userService">
 * 						<property name = "userMapper" ref="userMapper" />
 * 						<property name = "passwordEncoder" ref="passwordEncoder" />
 * 					</bean>
 * 					|	
 * 					|	
 * 
 * 					java (지금 시큐리티에서 xml쓰지 않으려고)
 * 						@Bean
 * 						puublic UserService userService(UserMapper userMapper, PasswordEncoder passwordEncoder){
 * 							Userservice userService = new Userservice();
 * 							userService.setUserMapper(userMapper);
 * 							userService.setPasswordEncoder(passwordEncoder);
 * 							
 * 							return userService;
 * 				}
 * 								필터체인지, 패스워드 엔코 시큐리티에서 필요하다.
 * 						
 * 
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
// EnableMethodSecurity : 메소드에서 접근제한을 하겟다는의미 
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
/*
		http
			// 요청 URL별로 리소스에 대한 접근권한을 설정한다.
			.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
	               .antMatchers("/resources/**").permitAll() 				// "/resources" 및 하위 경로에 대해서 접근을 허용한다.
	               .antMatchers("/", "/login", "/register").permitAll()		//"/","/login", "/register"에 대해서 접근을 허용한다.
	               .anyRequest().authenticated()); 			 				// 위에서 제시된 경로 외의 모든 경로에 대해서는 인증이 요구된다.
*/
		http
			// 모든요청 URL에 대해서 접근을 허용하도록 설정한다.
			// 단, 인증이 필요하거나, 권한이 필요한 경우 Controller 클래스의 요청 핸들러 메소드에서 접근제어를 설정한다. 
			.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
					.antMatchers("/**").permitAll());
		
		http
			//로그인 설정
			.formLogin((formLogin) -> formLogin
					.loginPage("/login")			// 로그인폼 페이지를 요청하는 URL은 "/login"이다
					.loginProcessingUrl("/login")	// 로그인 처리를 요청한 URL "/login"이다.
					.defaultSuccessUrl("/")			// 로그인이 성공하면 "/"를 재요청 URL로 지정한다.	
					.usernameParameter("id")		// username은 로그인폼에서 아이디값은 "id"라는 파라미터 이름으로 전달될 것이다.
					.passwordParameter("password")	// password는 로그인 폼에서 비밀번호값은 "password"라는 파라미터 이름으로 전달될것이다.
					.failureUrl("/login?error=fail"));	// 로그인에 실패하면 "/login?error"를 재요청URL로 지정한다.
		http
			//로그 아웃 설정
			.logout((logout) ->logout
					.logoutUrl("/logout")			// 로그아웃을 요청하는 URL은 "/logout"이다.
					.logoutSuccessUrl("/")			// 로그아웃이 성공하면"/"를 재요청 URL로 지정한다.
					.invalidateHttpSession(true));	// 로그아웃이 완료되면 HttpSession객체를 무효화시킨다.
		http
		// Spring Security 의 SecurityFilterChain의 필터에서 예외발생했을 때 예외처리하기
		// 인증(Authentication)에 대해서 AuthenticationException이 발생한다.
		// 인가(Authentication)에 대해서 AccessDeniedException이 발생한다.
		// 인증,인가 과정에서 발생하는 예외처리하는 사용자 정의 핸들러 구현해서 등록할 수 있으며, 따로 설정하지 않으면 기본으로 등록되어 있는 핸들러가 동작한다.
		// 인증 예외는 AuthencationEntryPoint 인터페이를 구현한 핸들러를 등록한다.
		// 인가 예외는 AccessDeniedHandler인터페이스를 구현한 핸드러를 등록한다.
		.exceptionHandling((exceptionHanding) -> exceptionHanding
				.accessDeniedHandler((request, response, accessDeniedException)->{
			response.sendRedirect("/accessdenied");
		}));
		
		 return http.build();
	}
	
	/*
	 * 아래의 메소드는 <bean /> 태그와 똑같은 역할을 수행하는 어노테이션이다.
	 * @bean 객체를 등록하겟다라는 의미다.
	 *	<bean id ="passwordEncoder" class="org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder"/>
	 *
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	//BCryptPasswordEncoder : 같은 비밀번호로 암호를 입력하고 회원가입을 해도 db에는 다른 암호화로 저장이된다.
	}
}

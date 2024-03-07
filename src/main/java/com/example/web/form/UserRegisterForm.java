package com.example.web.form;

import java.util.Date;

import com.example.vo.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserRegisterForm {
//@notBlank 폼입력값 유효성체크 무조건 값이 하나이상들어가 있어야한다.
//@notBlank 안에 메세지는 경고를 입력한다.
	
	@NotBlank(message = "아이디는 필수입력값입니다.")
	@Size(min = 3, max = 30, message = "아이디는 최소 3글자 최대 30글자를 초과할수 없습니다.")
	private String id;
	
	@NotBlank(message = "비밀번호는 필수입력값입니다." )
	@Size(min = 8, message = "비밀번호는 최소 8글자 이상만 가능합니다.")
	private String password;
	
	@NotBlank(message = "이름은 필수입력값입니다.")
	private String name;
	
	@NotBlank(message = "이메일은 필수입력값입니다.")
	@Email(message = "유효한 형식의 이메일이 아닙니다.")
	private String email;
	
	@NotBlank(message = "전환번호는 필수 입력값입니다.")
	private String tel;
	
	// 문자열이 아니기때문에 @NotNull로지정
	@NotNull(message = "생년월일은 필수 입력값입니다.")
	@Past(message = "생일은 오늘보다 이전 날짜만 가능합니다.")
	private Date birth;
	
	public User toUser() {
		User user = new User();
		user.setId(id);
		user.setPassword(password);
		user.setName(name);
		user.setEmail(email);
		user.setTel(tel);
		user.setBirth(birth);
		
		return user;
	}
}

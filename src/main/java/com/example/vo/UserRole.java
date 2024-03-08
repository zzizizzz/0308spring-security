package com.example.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
// 재정의
@NoArgsConstructor
// 매개변수초기화
@AllArgsConstructor
// 모든 매개변수초기화
public class UserRole {
	
	private int userNo;
	private String rolename;

}

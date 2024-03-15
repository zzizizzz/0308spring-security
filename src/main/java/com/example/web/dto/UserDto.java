package com.example.web.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

	// 내려보낼것들
	private int no;
	private String id;
	private String name;
	private String tel;
	private String email;
	@JsonFormat(pattern = "M월 d일") // 날짜변환
	private Date birth;
	@JsonFormat(pattern = "yyyy년 M월 d일") // 날짜변환
	private Date createdDate;
}

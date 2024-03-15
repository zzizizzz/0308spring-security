package com.example.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class User {

	private int no;
	private String id;
	// @JsonIgnore 비밀번호가 나오지 않도록
	@JsonIgnore
	private String password;
	private String name;
	private String email;
	private String tel;
	private String deleted;
	private Date birth;
	private Date createdDate;
	private Date updatedDate;
}

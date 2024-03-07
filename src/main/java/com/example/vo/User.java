package com.example.vo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class User {

	private int no;
	private String id;
	private String password;
	private String name;
	private String email;
	private String tel;
	private String deleted;
	private Date birth;
	private Date createdDate;
	private Date updatedDate;
}

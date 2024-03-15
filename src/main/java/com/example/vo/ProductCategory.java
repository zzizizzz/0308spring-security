package com.example.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor //기본생성자 만들어줌 
public class ProductCategory {
	
	private int no;
	private String name;
	private int parentNo;
	
	public ProductCategory(int no) {
		this.no = no;
	}

}

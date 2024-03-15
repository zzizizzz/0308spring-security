package com.example.web.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderForm {

	//1단계그대로 입력된값
	private int productNo;
	private String name;
	private int price;
	private int amount;
	private int totalPrice;
//	private String name;
//	private int price;
//	private int totalPrice;
	
	//2단계 입력된값
	private String payType;
	private String cardno;
	private int months;
	private int payAmount;
}

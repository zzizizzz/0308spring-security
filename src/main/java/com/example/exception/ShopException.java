package com.example.exception;

//super 부모의 생성자를 호출한다.
public class ShopException extends RuntimeException{
	
	private static final long serialVersionUID = 6500075737162637512L;

	public ShopException() {}
	
	public ShopException(String message) {
		super(message);
	}
	
	public ShopException(String message, Throwable cause) {
		super(message, cause);
	}

}

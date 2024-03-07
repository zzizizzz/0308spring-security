package com.example.exception;

public class AlreadyUsedEmailException extends ShopException{
	

	private static final long serialVersionUID = 6974693762699709632L;

	public AlreadyUsedEmailException(String message) {
		super(message);
	}

}

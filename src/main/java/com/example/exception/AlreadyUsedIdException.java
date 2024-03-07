package com.example.exception;

public class AlreadyUsedIdException extends ShopException {
	
	private static final long serialVersionUID = -1246580305351896433L;

	public AlreadyUsedIdException(String message) {
		super(message);
	}

}

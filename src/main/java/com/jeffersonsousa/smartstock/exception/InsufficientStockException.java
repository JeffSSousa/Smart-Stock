package com.jeffersonsousa.smartstock.exception;

public class InsufficientStockException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public InsufficientStockException(String msg) {
		super(msg);
	}

	
	
}

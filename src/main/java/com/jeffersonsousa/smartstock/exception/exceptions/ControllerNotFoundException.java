package com.jeffersonsousa.smartstock.exception.exceptions;

public class ControllerNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ControllerNotFoundException(String msg) {
		super(msg);
	}
}

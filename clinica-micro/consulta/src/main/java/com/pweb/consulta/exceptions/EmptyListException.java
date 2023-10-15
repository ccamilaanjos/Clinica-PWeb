package com.pweb.consulta.exceptions;

public class EmptyListException extends Exception {
	private final String message;
	
	public EmptyListException(String message) {
		 this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}

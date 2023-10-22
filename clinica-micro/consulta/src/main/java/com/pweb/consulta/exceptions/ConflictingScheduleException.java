package com.pweb.consulta.exceptions;

public class ConflictingScheduleException extends RuntimeException {
	
	public ConflictingScheduleException (String message) {
		super(message);
	}
}
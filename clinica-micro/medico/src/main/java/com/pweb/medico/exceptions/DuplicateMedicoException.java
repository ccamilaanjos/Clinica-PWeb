package com.pweb.medico.exceptions;

public class DuplicateMedicoException extends RuntimeException {

	public DuplicateMedicoException() {
		super("CRM já cadastrado");
	}
	
}

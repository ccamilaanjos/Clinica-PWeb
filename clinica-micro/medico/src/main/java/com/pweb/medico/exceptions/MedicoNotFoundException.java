package com.pweb.medico.exceptions;

public class MedicoNotFoundException extends RuntimeException {
	public MedicoNotFoundException() {
		super("Médico não encontrado");
	}
}

package com.pweb.clinica.exceptions;

public class MedicoNotFoundException extends Exception {
	public MedicoNotFoundException() {
		super("Médico não encontrado");
	}
}

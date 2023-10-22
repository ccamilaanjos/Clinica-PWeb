package com.pweb.paciente.exceptions;

public class PacienteNotFoundException extends RuntimeException {
	
	public PacienteNotFoundException() {
		super("Paciente não encontrado");
	}
}

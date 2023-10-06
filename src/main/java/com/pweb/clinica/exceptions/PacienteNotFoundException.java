package com.pweb.clinica.exceptions;

public class PacienteNotFoundException extends Exception {
	public PacienteNotFoundException() {
		super("Paciente não encontrado");
	}
}

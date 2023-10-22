package com.pweb.paciente.exceptions;

public class DuplicatePacienteException extends RuntimeException {
	private final String message = "CPF já cadastrado";

	public DuplicatePacienteException() {
		super("CPF já cadastrtado");
	}
}

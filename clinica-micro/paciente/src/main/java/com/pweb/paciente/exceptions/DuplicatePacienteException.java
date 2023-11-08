package com.pweb.paciente.exceptions;

public class DuplicatePacienteException extends RuntimeException {

	public DuplicatePacienteException() {
		super("CPF já cadastrtado");
	}
}

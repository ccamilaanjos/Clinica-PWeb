package com.pweb.clinica.exceptions;

public class PessoaInativaException extends Exception {
	public PessoaInativaException() {
		super("Médico e/ou paciente inativo no sistema");
	}
}

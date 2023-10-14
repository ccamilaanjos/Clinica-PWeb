package com.pweb.clinica.exceptions;

public class ConsultaNotFoundException extends Exception {
	public ConsultaNotFoundException() {
		super("Consulta não encontrada");
	}
}

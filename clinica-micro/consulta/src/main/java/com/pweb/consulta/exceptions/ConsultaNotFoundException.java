package com.pweb.consulta.exceptions;

public class ConsultaNotFoundException extends RuntimeException {
	
	public ConsultaNotFoundException() {
		super("Consulta não encontrada");
	}
}

package com.pweb.consulta.exceptions;

public class ConsultaNotFoundException extends Exception {
	public ConsultaNotFoundException() {
		super("Consulta não encontrada");
	}
}

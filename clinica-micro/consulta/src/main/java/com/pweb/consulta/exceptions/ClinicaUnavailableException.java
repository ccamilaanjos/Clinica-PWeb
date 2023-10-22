package com.pweb.consulta.exceptions;

public class ClinicaUnavailableException extends RuntimeException {
	
	public ClinicaUnavailableException() {
		super("A clínica não está disponível para marcações no momento informado");
	}
}

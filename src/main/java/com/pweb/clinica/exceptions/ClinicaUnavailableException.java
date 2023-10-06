package com.pweb.clinica.exceptions;

public class ClinicaUnavailableException extends Exception {
	public ClinicaUnavailableException() {
		super("A clínica não está disponível para marcações no momento informado");
	}
}

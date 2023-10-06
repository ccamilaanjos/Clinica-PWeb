package com.pweb.clinica.exceptions;

public class PacienteJaTemConsultaMarcadaException extends Exception {
	public PacienteJaTemConsultaMarcadaException() {
		super("Este paciente já tem consulta marcada neste horário e data");
	}
}

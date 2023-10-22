package com.pweb.medico.exceptions;

public class EspecialidadeNotFoundException extends RuntimeException {

	public EspecialidadeNotFoundException() {
		super("Especialidade não encontrada");
	}
	
}

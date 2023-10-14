package com.pweb.paciente.exceptions;

public class EntityNotFoundException extends Exception {
	public EntityNotFoundException(String entity) {
		super("Não encontrado: " + entity);
	}
}

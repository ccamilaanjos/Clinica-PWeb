package com.pweb.clinica.exceptions;

public class EntityNotFoundException extends Exception {
	public EntityNotFoundException(String entity) {
		super("Não encontrado: " + entity);
	}
}

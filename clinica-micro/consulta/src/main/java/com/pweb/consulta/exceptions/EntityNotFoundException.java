package com.pweb.consulta.exceptions;

public class EntityNotFoundException extends RuntimeException {
	
	public EntityNotFoundException(String entity) {
		super("Não encontrado: " + entity);
	}
}

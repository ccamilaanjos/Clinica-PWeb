package com.pweb.clinica.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface PessoaController<T> {
	
	public abstract List<?> listar();
	public abstract ResponseEntity<?> cadastrar(T dto);
	public abstract ResponseEntity<?> atualizar();
	public abstract ResponseEntity<?> remover(Long id);
}

package com.pweb.clinica.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface PessoaController {
	
	public abstract List<?> listar();
	public abstract ResponseEntity<?> cadastrar();
	public abstract ResponseEntity<?> atualizar();
	public abstract ResponseEntity<?> remover(Long id);
}

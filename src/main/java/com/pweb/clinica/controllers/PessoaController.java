package com.pweb.clinica.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface PessoaController<T> {
	
	public Page<?> listar(Pageable pageable);
	public ResponseEntity<?> cadastrar(T dto);
	public ResponseEntity<?> atualizar();
	public ResponseEntity<?> remover(Long id);
}

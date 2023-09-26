package com.pweb.clinica.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface PessoaController<FormDTO, DTO> {
	
	public ResponseEntity<Page<DTO>> listar(Pageable pageable);
	public ResponseEntity<?> cadastrar(FormDTO dto);
	public ResponseEntity<?> atualizar(Long id, FormDTO dto);
	public ResponseEntity<?> remover(Long id);
}

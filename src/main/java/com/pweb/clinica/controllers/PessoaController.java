package com.pweb.clinica.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface PessoaController<PostDTO, PutDTO, DTO> {
	
	public ResponseEntity<Page<DTO>> listar(int page);
	public ResponseEntity<?> cadastrar(PostDTO dto);
	public ResponseEntity<?> atualizar(Long id, PutDTO dto);
	public ResponseEntity<?> remover(Long id);
}

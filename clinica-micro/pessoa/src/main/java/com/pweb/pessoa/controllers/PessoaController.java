package com.pweb.pessoa.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface PessoaController<PostDTO, GetDTO, PutDTO, DTO> {
	
	public ResponseEntity<Page<GetDTO>> listarTodos(int page);
	public ResponseEntity<Page<GetDTO>> listarAtivos(int page);
	public ResponseEntity<?> cadastrar(PostDTO dto);
	public ResponseEntity<?> atualizar(Long id, PutDTO dto);
	public ResponseEntity<?> remover(Long id);
}

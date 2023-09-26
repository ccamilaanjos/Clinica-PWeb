package com.pweb.clinica.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.pweb.clinica.dtos.PacienteDTO;

public interface PessoaController<FormDTO, DTO> {
	
	public ResponseEntity<Page<DTO>> listar(Pageable pageable);
	public ResponseEntity<?> cadastrar(FormDTO dto);
	public ResponseEntity<?> atualizar();
	public ResponseEntity<?> remover(Long id);
}

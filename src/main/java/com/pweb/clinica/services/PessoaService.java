package com.pweb.clinica.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pweb.clinica.models.Pessoa;

public interface PessoaService<Model, PostDTO, PutDTO, DTO> {
	
	public Page<DTO> getPagina(Pageable pageable);
	public Pessoa cadastrar(PostDTO formDto);
	public Model atualizar(Long id, PutDTO formDto) throws Exception; 
	public Pessoa tornarInativo(Long id);
	public Optional<Model> buscarPorID(Long id);
}

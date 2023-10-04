package com.pweb.clinica.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PessoaService<Model, PostDTO, PutDTO, DTO> {
	public Page<DTO> getPagina(Pageable pageable);
	public DTO cadastrar(PostDTO formDto) throws Exception;
	public DTO atualizar(Long id, PutDTO formDto) throws Exception; 
	public Model tornarInativo(Long id);
	public Optional<Model> buscarPorID(Long id);
}

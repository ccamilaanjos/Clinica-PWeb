package com.pweb.pessoa.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PessoaService<GetDTO, PostDTO, PutDTO, DTO> {
	public Page<GetDTO> getPagina(Pageable pageable, String type);
	public DTO cadastrar(PostDTO formDto) throws Exception;
	public DTO atualizar(Long id, PutDTO formDto) throws Exception; 
	public void remover(Long id) throws Exception;
}

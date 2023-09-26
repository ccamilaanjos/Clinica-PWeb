package com.pweb.clinica.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pweb.clinica.models.Pessoa;

public interface PessoaService<Model, FormDTO, DTO> {
	
	public Page<DTO> getPagina(Pageable pageable);
	public DTO converterParaDTO(Model model);
	public Pessoa cadastrar(FormDTO formDto);
	public Model atualizar(Long id, FormDTO formDto); 
	public Model salvarDados(Model model, FormDTO formDto);
	public Pessoa tornarInativo(Long id);
	public Optional<Model> buscarPorID(Long id);
}

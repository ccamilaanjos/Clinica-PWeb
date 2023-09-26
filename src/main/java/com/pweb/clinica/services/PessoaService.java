package com.pweb.clinica.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.pweb.clinica.models.Pessoa;

public interface PessoaService<T> {
	
	public List<?> converterLista(List<?> list);
	public List<?> getListaOrdenadaPorNome();
	public Pessoa cadastrar(T dto);
	public ResponseEntity<?> tornarInativo(Long id);
	public Optional<?> buscarPorID(Long id);
}

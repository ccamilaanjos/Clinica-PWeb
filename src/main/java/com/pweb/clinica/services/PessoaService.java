package com.pweb.clinica.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface PessoaService {
	
	public List<?> converterLista(List<?> list);
	public List<?> getListaOrdenadaPorNome();
	public ResponseEntity<?> cadastrar();
	public ResponseEntity<?> tornarInativo();
}

package com.pweb.clinica.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pweb.clinica.dtos.MedicoDTO;
import com.pweb.clinica.models.Medico;
import com.pweb.clinica.repositories.MedicoRepository;

@Service
public class MedicoService implements PessoaService {
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Override
	public List<MedicoDTO> converterLista(List<?> list) {
		return list.stream().map(medico -> new MedicoDTO((Medico) medico)).collect(Collectors.toList());
	}
	
	@Override
	public List<MedicoDTO> getListaOrdenadaPorNome() {
		return converterLista(medicoRepository.findAll(Sort.by(Sort.Direction.ASC, "nome")));
	}

	@Override
	public ResponseEntity<?> cadastrar() {
		// TODO
		return null;
	}

	@Override
	public ResponseEntity<?> tornarInativo() {
		// TODO
		return null;
	}
	
}

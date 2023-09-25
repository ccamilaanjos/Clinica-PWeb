package com.pweb.clinica.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import com.pweb.clinica.dtos.PacienteDTO;
import com.pweb.clinica.models.Paciente;
import com.pweb.clinica.repositories.PacienteRepository;

public class PacienteService implements PessoaService {
	
	@Autowired
	private PacienteRepository pacienteRepository;

	@Override
	public List<PacienteDTO> converterLista(List<?> list) {
		return list.stream().map(paciente -> new PacienteDTO((Paciente) paciente)).collect(Collectors.toList());
	}

	@Override
	public List<PacienteDTO> getListaOrdenadaPorNome() {
		return converterLista(pacienteRepository.findAll(Sort.by(Sort.Direction.ASC, "nome")));
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

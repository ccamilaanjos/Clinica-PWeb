package com.pweb.medico.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pweb.medico.dtos.EspecialidadeDTO;
import com.pweb.medico.enums.EspecialidadeTipo;
import com.pweb.medico.exceptions.EspecialidadeNotFoundException;
import com.pweb.medico.models.Especialidade;
import com.pweb.medico.repositories.EspecialidadeRepository;

import jakarta.annotation.PostConstruct;

@Service
public class EspecialidadeService {
	@Autowired
	private EspecialidadeRepository especialidadeRepository;

	@PostConstruct
	private void carregarDados() {
		List<Especialidade> espDB = especialidadeRepository.findAll();
		if(!espDB.isEmpty()) {
			return;
		}
		
		List<EspecialidadeTipo> especialidades = EspecialidadeTipo.obterTipos();
		
		for(EspecialidadeTipo e : especialidades) {
			Especialidade especialidade = new Especialidade();
			especialidade.setTitulo(e.toString());
			especialidadeRepository.save(especialidade);
		}
	}
	
	public Especialidade buscarPorId(Long idEspecialidade) throws EspecialidadeNotFoundException {
		return especialidadeRepository.findById(idEspecialidade).orElseThrow(EspecialidadeNotFoundException::new);
	}
	
	public Especialidade buscarPorTitulo(String titulo) throws EspecialidadeNotFoundException {
		return especialidadeRepository.findByTituloIgnoreCase(titulo).orElseThrow(EspecialidadeNotFoundException::new);
	}
	
	public List<EspecialidadeDTO> buscarTodas() {
		List<Especialidade> especialidades = especialidadeRepository.findAll();
		return especialidades.stream().map(EspecialidadeDTO :: new).collect(Collectors.toList());
	}
}

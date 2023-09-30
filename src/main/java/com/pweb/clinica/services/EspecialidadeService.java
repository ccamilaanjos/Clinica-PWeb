package com.pweb.clinica.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pweb.clinica.enums.EspecialidadeTipo;
import com.pweb.clinica.exceptions.EspecialidadeNotFoundException;
import com.pweb.clinica.models.Especialidade;
import com.pweb.clinica.repositories.EspecialidadeRepository;

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
			System.out.println("Especialidade: " + e.toString());
			especialidadeRepository.save(especialidade);
		}
	}
	
	public Especialidade verificarEspecialidade (String especialidade) {
		Optional<Especialidade> optionalEspecialidade = buscarPorTitulo(especialidade);
		if(optionalEspecialidade.isEmpty()) {
			return null;
		}
		return optionalEspecialidade.get();
	}
	
	public Optional<Especialidade> buscarPorTitulo(String titulo){
		return especialidadeRepository.findByTituloIgnoreCase(titulo);
	}
}

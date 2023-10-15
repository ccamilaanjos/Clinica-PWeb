package com.pweb.medico.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pweb.medico.enums.EspecialidadeTipo;
import com.pweb.medico.exceptions.EntityNotFoundException;
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
	
	public Especialidade buscarPorId(Long idEspecialidade) throws EntityNotFoundException {
		return especialidadeRepository.findById(idEspecialidade).orElseThrow(() -> new EntityNotFoundException("Especialidade"));
	}
	
	public Especialidade buscarPorTitulo(String titulo) throws EntityNotFoundException {
		return especialidadeRepository.findByTituloIgnoreCase(titulo).orElseThrow(() -> new EntityNotFoundException("Especialidade"));
	} 
}

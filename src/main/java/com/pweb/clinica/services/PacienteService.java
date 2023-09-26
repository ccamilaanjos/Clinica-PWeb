package com.pweb.clinica.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pweb.clinica.dtos.PacienteDTO;
import com.pweb.clinica.dtos.PacienteFormDTO;
import com.pweb.clinica.models.Paciente;
import com.pweb.clinica.repositories.PacienteRepository;

@Service
public class PacienteService implements PessoaService<PacienteFormDTO> {
	
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
	public Paciente cadastrar(PacienteFormDTO pacienteForm) {
		Paciente paciente = new Paciente();
		paciente.setNome(pacienteForm.nome());
		paciente.setEmail(pacienteForm.email());
		paciente.setEndereco(pacienteForm.endereco());
		paciente.setTelefone(pacienteForm.telefone());
		pacienteRepository.save(paciente);
		return paciente;
	}

	@Override
	public Paciente tornarInativo(Long id) {
		Optional<Paciente> optionalPaciente = buscarPorID(id);
		
		if(optionalPaciente.isEmpty()) {
			return null;
		}
		
		Paciente paciente = optionalPaciente.get();
		paciente.setAtivo(false);
		pacienteRepository.save(paciente);
		
		return paciente;
	}

	@Override
	public Optional<Paciente> buscarPorID(Long id) {
		return pacienteRepository.findById(id);
	}
}

package com.pweb.clinica.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pweb.clinica.dtos.PacienteDTO;
import com.pweb.clinica.dtos.PacienteFormDTO;
import com.pweb.clinica.models.Paciente;
import com.pweb.clinica.repositories.PacienteRepository;

@Service
public class PacienteService implements PessoaService<Paciente, PacienteFormDTO, PacienteDTO> {

	@Autowired
	private PacienteRepository pacienteRepository;

	public Page<PacienteDTO> getPagina(Pageable pageable) {
		return pacienteRepository.findAll(pageable).map(this::converterParaDTO);
	}

	public PacienteDTO converterParaDTO(Paciente paciente) {
		return new PacienteDTO(paciente.getId(), paciente.getNome(), paciente.getCPF(), paciente.getEmail(), paciente.getTelefone(),
				paciente.getEndereco(), paciente.getAtivo());
	}

	@Override
	public Paciente cadastrar(PacienteFormDTO pacienteForm) {
		Paciente paciente = new Paciente();
		paciente.setEmail(pacienteForm.email());
		paciente.setCPF(pacienteForm.CPF());
		salvarDados(paciente, pacienteForm);
		return paciente;
	}
	
	@Override
	public Paciente atualizar(Long id, PacienteFormDTO pacienteForm) {
		Optional<Paciente> optionalPaciente = buscarPorID(id);
		if (optionalPaciente.isEmpty()) {
			return null;
		}
		
		Paciente paciente = salvarDados(optionalPaciente.get(), pacienteForm);
		return paciente;
	}
	
	@Override
	public Paciente salvarDados(Paciente paciente, PacienteFormDTO pacienteForm) {
		paciente.setNome(pacienteForm.nome());
		paciente.setEndereco(pacienteForm.endereco());
		paciente.setTelefone(pacienteForm.telefone());
		pacienteRepository.save(paciente);
		return paciente;
	}

	@Override
	public Paciente tornarInativo(Long id) {
		Optional<Paciente> optionalPaciente = buscarPorID(id);

		if (optionalPaciente.isEmpty()) {
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
package com.pweb.clinica.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pweb.clinica.converters.EnderecoConverter;
import com.pweb.clinica.dtos.PacienteDTO;
import com.pweb.clinica.dtos.PacientePostDTO;
import com.pweb.clinica.dtos.PacientePutDTO;
import com.pweb.clinica.exceptions.PacienteNotFoundException;
import com.pweb.clinica.models.Endereco;
import com.pweb.clinica.models.Paciente;
import com.pweb.clinica.repositories.MedicoRepository;
import com.pweb.clinica.repositories.PacienteRepository;

@Service
public class PacienteService implements PessoaService<Paciente, PacientePostDTO, PacientePutDTO, PacienteDTO> {

	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Autowired
	private EnderecoService enderecoService;

	public Page<PacienteDTO> getPagina(Pageable pageable) {
		return pacienteRepository.findAll(pageable).map(this::converterParaDTO);
	}

	public PacienteDTO converterParaDTO(Paciente paciente) {
		return new PacienteDTO(paciente.getId(), paciente.getNome(), paciente.getCPF(), paciente.getEmail(), paciente.getTelefone(),
				paciente.getEndereco(), paciente.getAtivo());
	}

	@Override
	public Paciente cadastrar(PacientePostDTO pacienteForm) {
		Paciente paciente = new Paciente();
		paciente.setEmail(pacienteForm.email());
		paciente.setCPF(pacienteForm.cpf());
		paciente.setTelefone(pacienteForm.telefone());
		
		Endereco endereco = EnderecoConverter.converterDtoParaModel(pacienteForm.endereco());
		paciente.setEndereco(atribuirEndereco(endereco));
		pacienteRepository.save(paciente);
		
		return paciente;
	}
	
	@Override
	public Paciente atualizar(Long id, PacientePutDTO pacienteForm) throws PacienteNotFoundException {
		Optional<Paciente> optionalPaciente = buscarPorID(id);
		if (optionalPaciente.isEmpty()) {
			throw new PacienteNotFoundException();
		}
		
		Paciente paciente = optionalPaciente.get();
		paciente.setNome(pacienteForm.nome());
		paciente.setTelefone(pacienteForm.telefone());
		
		Endereco endereco = enderecoService.ajustarCampos(paciente.getEndereco(), pacienteForm.endereco());
		paciente.setEndereco(atribuirEndereco(endereco));
		pacienteRepository.save(paciente);
		
		return paciente;
	}
	
	private Endereco atribuirEndereco(Endereco enderecoForm) {
		Optional<Endereco> enderecoExistente = enderecoService.buscarEnderecoExistente(enderecoForm);
		if(enderecoExistente.isPresent()) {
			return enderecoExistente.get();
		}
		
		return enderecoForm;
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
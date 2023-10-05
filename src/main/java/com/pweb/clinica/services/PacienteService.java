package com.pweb.clinica.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pweb.clinica.dtos.EnderecoFormDTO;
import com.pweb.clinica.dtos.PacienteDTO;
import com.pweb.clinica.dtos.PacienteGetDTO;
import com.pweb.clinica.dtos.PacientePostDTO;
import com.pweb.clinica.dtos.PacientePutDTO;
import com.pweb.clinica.exceptions.DuplicatePacienteException;
import com.pweb.clinica.exceptions.PacienteNotFoundException;
import com.pweb.clinica.models.Endereco;
import com.pweb.clinica.models.Paciente;
import com.pweb.clinica.repositories.PacienteRepository;

@Service
public class PacienteService implements PessoaService<Paciente, PacienteGetDTO, PacientePostDTO, PacientePutDTO, PacienteDTO> {

	@Autowired
	private PacienteRepository pacienteRepository;
	@Autowired
	private EnderecoService enderecoService;

	public Page<PacienteGetDTO> getPagina(Pageable pageable) {
		return pacienteRepository.findAll(pageable).map(PacienteGetDTO::new);
	}

	@Override
	public PacienteDTO cadastrar(PacientePostDTO pacienteForm) throws DuplicatePacienteException {
		Optional<Paciente> cpfExistente = pacienteRepository.findByCpf(pacienteForm.cpf());
		if(cpfExistente.isPresent()) {
			throw new DuplicatePacienteException(new PacienteDTO(cpfExistente.get()));
		}
		
		Endereco endereco = enderecoService.atribuirEndereco(pacienteForm.endereco());
		Paciente paciente = new Paciente(pacienteForm, endereco);
		
		pacienteRepository.save(paciente);
		return new PacienteDTO(paciente);
	}
	
	@Override
	public PacienteDTO atualizar(Long id, PacientePutDTO pacienteForm) throws PacienteNotFoundException {
		Paciente paciente = pacienteRepository.findById(id).orElseThrow(PacienteNotFoundException::new);
		
		paciente.setNome(pacienteForm.nome());
		paciente.setTelefone(pacienteForm.telefone());
		
		Endereco endereco = enderecoService.ajustarCampos(paciente.getEndereco(), pacienteForm.endereco());
		EnderecoFormDTO enderecoAjustado = new EnderecoFormDTO(endereco);
		paciente.setEndereco(enderecoService.atribuirEndereco(enderecoAjustado));
		pacienteRepository.save(paciente);
		
		return new PacienteDTO(paciente);
	}
	
	@Override
	public Paciente tornarInativo(Long id) {
		Optional<Paciente> optionalPaciente = pacienteRepository.findById(id);

		if (optionalPaciente.isEmpty()) {
			return null;
		}

		Paciente paciente = optionalPaciente.get();
		paciente.setAtivo(false);
		pacienteRepository.save(paciente);

		return paciente;
	}	
}
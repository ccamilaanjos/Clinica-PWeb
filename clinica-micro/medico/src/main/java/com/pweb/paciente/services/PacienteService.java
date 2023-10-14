package com.pweb.paciente.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pweb.paciente.clients.EnderecoClient;
import com.pweb.paciente.clients.EnderecoPutDTO;
import com.pweb.paciente.dtos.PacienteDTO;
import com.pweb.paciente.dtos.PacienteGetDTO;
import com.pweb.paciente.dtos.PacientePostDTO;
import com.pweb.paciente.dtos.PacientePutDTO;
import com.pweb.paciente.exceptions.DuplicatePacienteException;
import com.pweb.paciente.exceptions.EntityNotFoundException;
import com.pweb.paciente.models.Paciente;
import com.pweb.paciente.repositories.PacienteRepository;

@Service
public class PacienteService implements PessoaService<PacienteGetDTO, PacientePostDTO, PacientePutDTO, PacienteDTO> {

	@Autowired
	private PacienteRepository pacienteRepository;
	@Autowired
	private EnderecoClient enderecoClient;

	public Page<PacienteGetDTO> getPagina(Pageable pageable, String type) {
		if(type.equalsIgnoreCase("all")) {
			return pacienteRepository.findAll(pageable).map(PacienteGetDTO::new);			
		}
		return pacienteRepository.findAllByAtivoTrue(pageable).map(PacienteGetDTO::new);
	}

	@Override
	public PacienteDTO cadastrar(PacientePostDTO pacienteForm) throws DuplicatePacienteException {
		Optional<Paciente> cpfExistente = pacienteRepository.findByCpf(pacienteForm.cpf());
		if(cpfExistente.isPresent()) {
			throw new DuplicatePacienteException(new PacienteDTO(cpfExistente.get()));
		}
		
		Long endereco = enderecoClient.cadastrar(pacienteForm.endereco()).getBody();
		Paciente paciente = new Paciente(pacienteForm, endereco);
		
		pacienteRepository.save(paciente);
		return new PacienteDTO(paciente);
	}
	
	@Override
	public PacienteDTO atualizar(Long id, PacientePutDTO pacienteForm) throws EntityNotFoundException {
		Paciente paciente = pacienteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Paciente"));
		
		paciente.setNome(pacienteForm.nome());
		paciente.setTelefone(pacienteForm.telefone());
		
		EnderecoPutDTO enderecoForm = new EnderecoPutDTO(paciente.getEndereco(), pacienteForm.endereco());
		Long endereco = enderecoClient.atualizar(enderecoForm).getBody();
		paciente.setEndereco(endereco);
		pacienteRepository.save(paciente);
		
		return new PacienteDTO(paciente);
	}
	
	@Override
	public void tornarInativo(Long id) throws EntityNotFoundException {
		Paciente paciente = pacienteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Paciente"));

		paciente.setAtivo(false);
		pacienteRepository.save(paciente);
	}
	
	public Paciente buscarPacienteAtivo(Long idPaciente) throws EntityNotFoundException {
		return pacienteRepository.findByIdAndAtivoTrue(idPaciente).orElseThrow(() -> new EntityNotFoundException("Paciente"));
	}
}
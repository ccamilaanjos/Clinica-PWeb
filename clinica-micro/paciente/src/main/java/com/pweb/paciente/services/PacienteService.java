package com.pweb.paciente.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pweb.paciente.clients.EnderecoClient;
import com.pweb.paciente.clients.EnderecoGetDTO;
import com.pweb.paciente.clients.EnderecoPutDTO;
import com.pweb.paciente.dtos.PacienteDTO;
import com.pweb.paciente.dtos.PacienteGetDTO;
import com.pweb.paciente.dtos.PacienteGetDTO2;
import com.pweb.paciente.dtos.PacientePostDTO;
import com.pweb.paciente.dtos.PacientePutDTO;
import com.pweb.paciente.exceptions.DuplicatePacienteException;
import com.pweb.paciente.exceptions.PacienteNotFoundException;
import com.pweb.paciente.models.Paciente;
import com.pweb.paciente.repositories.PacienteRepository;
import com.pweb.pessoa.services.PessoaService;

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
			throw new DuplicatePacienteException();
		}
		
		Long endereco = enderecoClient.cadastrar(pacienteForm.endereco()).getBody();
		Paciente paciente = new Paciente(pacienteForm, endereco);
		
		pacienteRepository.save(paciente);
		return new PacienteDTO(paciente);
	}
	
	@Override
	public PacienteDTO atualizar(Long id, PacientePutDTO pacienteForm) throws PacienteNotFoundException {
		Paciente paciente = pacienteRepository.findById(id).orElseThrow(PacienteNotFoundException::new);
		return atualizarCampos(paciente, pacienteForm);
	}
	
	public PacienteDTO atualizar(String cpf, PacientePutDTO pacienteForm) throws PacienteNotFoundException {
		Paciente paciente = pacienteRepository.findByCpf(cpf).orElseThrow(PacienteNotFoundException::new);
		return atualizarCampos(paciente, pacienteForm);
	}
	
	public PacienteDTO atualizarCampos(Paciente paciente, PacientePutDTO pacienteForm) {
		paciente.setNome(pacienteForm.nome());
		paciente.setTelefone(pacienteForm.telefone());
		
		EnderecoPutDTO enderecoForm = new EnderecoPutDTO(paciente.getEndereco(), pacienteForm.endereco());
		Long endereco = enderecoClient.atualizar(enderecoForm).getBody();
		paciente.setEndereco(endereco);
		
		pacienteRepository.save(paciente);
		return new PacienteDTO(paciente);
	}
	
	@Override
	public void remover(Long id) throws PacienteNotFoundException {
		Paciente paciente = pacienteRepository.findById(id).orElseThrow(PacienteNotFoundException::new);
		tornarInativo(paciente);
	}
	
	public void remover(String cpf) throws PacienteNotFoundException {
		Paciente paciente = pacienteRepository.findByCpf(cpf).orElseThrow(PacienteNotFoundException::new);
		tornarInativo(paciente);
	}
	
	public void tornarInativo(Paciente paciente) {
		paciente.setAtivo(false);
		pacienteRepository.save(paciente);
	}
	
	public Paciente buscarPacienteIDAtivo(Long idPaciente) throws PacienteNotFoundException {
		return pacienteRepository.findByIdAndAtivoTrue(idPaciente).orElseThrow(PacienteNotFoundException::new);
	}
	
	public Paciente buscarPacienteCPFAtivo(String cpf) throws PacienteNotFoundException {
		return pacienteRepository.findByCpfAndAtivoTrue(cpf).orElseThrow(PacienteNotFoundException::new);
	}
	
	public PacienteGetDTO2 encontrarDados(String cpf) {
		Paciente paciente = buscarPacienteCPFAtivo(cpf);
		EnderecoGetDTO endereco = enderecoClient.buscar(paciente.getEndereco()).getBody();
		return new PacienteGetDTO2(paciente, endereco);
	}
}

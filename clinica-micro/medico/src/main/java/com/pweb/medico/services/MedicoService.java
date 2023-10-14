package com.pweb.medico.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pweb.medico.clients.EnderecoClient;
import com.pweb.medico.clients.EnderecoPutDTO;
import com.pweb.medico.dtos.MedicoDTO;
import com.pweb.medico.dtos.MedicoGetDTO;
import com.pweb.medico.dtos.MedicoPostDTO;
import com.pweb.medico.dtos.MedicoPutDTO;
import com.pweb.medico.exceptions.DuplicateMedicoException;
import com.pweb.medico.exceptions.EntityNotFoundException;
import com.pweb.medico.models.Especialidade;
import com.pweb.medico.models.Medico;
import com.pweb.medico.repositories.EspecialidadeRepository;
import com.pweb.medico.repositories.MedicoRepository;

@Service
public class MedicoService implements PessoaService<MedicoGetDTO, MedicoPostDTO, MedicoPutDTO, MedicoDTO> {

	@Autowired
	private MedicoRepository medicoRepository;
	@Autowired
	private EnderecoClient enderecoClient;
	@Autowired
	private EspecialidadeRepository especialidadeRepository;

	@Override
	public Page<MedicoGetDTO> getPagina(Pageable pageable, String type) {
		if(type.equalsIgnoreCase("all")) {
			return medicoRepository.findAll(pageable).map(medico -> new MedicoGetDTO(medico, medico.getEspecialidade()));			
		}
		return medicoRepository.findAllByAtivoTrue(pageable).map(medico -> new MedicoGetDTO(medico, medico.getEspecialidade()));
	}
	
	public List<MedicoGetDTO> buscarMedicosPorEspecialidade(Long id) throws EntityNotFoundException {
		Especialidade especialidade = especialidadeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Especialidade"));
		
		List<Medico> medicos = medicoRepository.findByEspecialidade_idOrderByNomeAsc(id).orElse(null);
		return medicos.stream().map(medico -> new MedicoGetDTO(medico, especialidade)).collect(Collectors.toList());
	}

	@Override
	public MedicoDTO cadastrar(MedicoPostDTO medicoForm) throws EntityNotFoundException, DuplicateMedicoException {
		Optional<Medico> crmExistente = medicoRepository.findByCrm(medicoForm.crm());
		if(crmExistente.isPresent()) {
			throw new DuplicateMedicoException(new MedicoDTO(crmExistente.get()));
		}
		
		Especialidade especialidade = especialidadeRepository.findByTituloIgnoreCase(medicoForm.especialidade())
				.orElseThrow(() -> new EntityNotFoundException("Especialidade"));

		Long endereco = enderecoClient.cadastrar(medicoForm.endereco()).getBody();
		Medico medico = new Medico(medicoForm, especialidade, endereco);
		
		medicoRepository.save(medico);
		return new MedicoDTO(medico);
	}

	@Override
	public MedicoDTO atualizar(Long id, MedicoPutDTO medicoForm) throws EntityNotFoundException {
		Medico medico = medicoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Medico"));
		
		medico.setNome(medicoForm.nome());
		medico.setTelefone(medicoForm.telefone());

		EnderecoPutDTO enderecoForm = new EnderecoPutDTO(medico.getEndereco(), medicoForm.endereco());
		Long endereco = enderecoClient.atualizar(enderecoForm).getBody();
		medico.setEndereco(endereco);
		
		medicoRepository.save(medico);
		return new MedicoDTO(medico);
	}

	@Override
	public void tornarInativo(Long id) throws EntityNotFoundException {
		Medico medico = medicoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Medico"));

		medico.setAtivo(false);
		medicoRepository.save(medico);
	}
	
	public Medico buscarMedicoAtivo(Long idMedico) throws EntityNotFoundException {
		return medicoRepository.findByIdAndAtivoTrue(idMedico).orElseThrow(() -> new EntityNotFoundException("Medico"));
	}
	
//	public List<Medico> buscarMedicosDisponiveis(Long idEspecialidade, Long idMedico, LocalDate data, LocalTime horario) {
//		return medicoRepository.findMedicosDisponiveis(
//				idEspecialidade, data, horario.minusHours(1), horario.plusHours(1));
//	}
}

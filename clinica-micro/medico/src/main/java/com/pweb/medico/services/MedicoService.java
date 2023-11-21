package com.pweb.medico.services;

import java.util.List;
import java.util.Optional;

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
import com.pweb.medico.exceptions.EspecialidadeNotFoundException;
import com.pweb.medico.exceptions.MedicoNotFoundException;
import com.pweb.medico.models.Especialidade;
import com.pweb.medico.models.Medico;
import com.pweb.medico.repositories.MedicoRepository;
import com.pweb.pessoa.services.PessoaService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MedicoService implements PessoaService<MedicoGetDTO, MedicoPostDTO, MedicoPutDTO, MedicoDTO> {

	@Autowired
	private MedicoRepository medicoRepository;
	@Autowired
	private EnderecoClient enderecoClient;
	@Autowired
	private EspecialidadeService especialidadeService;

	@Override
	public Page<MedicoGetDTO> getPagina(Pageable pageable, String type) {
		if(type.equalsIgnoreCase("all")) {
			return medicoRepository.findAll(pageable).map(medico -> new MedicoGetDTO(medico));			
		}
		return medicoRepository.findAllByAtivoTrue(pageable).map(medico -> new MedicoGetDTO(medico));
	}
	

	@Override
	public MedicoDTO cadastrar(MedicoPostDTO medicoForm)
			throws MedicoNotFoundException, EspecialidadeNotFoundException, DuplicateMedicoException {
		Optional<Medico> crmExistente = medicoRepository.findByCrm(medicoForm.crm());
		if(crmExistente.isPresent()) {
			throw new DuplicateMedicoException();
		}
		
		Especialidade especialidade = especialidadeService.buscarPorTitulo(medicoForm.especialidade());

		Long endereco = enderecoClient.cadastrar(medicoForm.endereco()).getBody();
		Medico medico = new Medico(medicoForm, especialidade.getId(), endereco);
		
		medicoRepository.save(medico);
		return new MedicoDTO(medico);
	}

	@Override
	public MedicoDTO atualizar(Long id, MedicoPutDTO medicoForm) throws MedicoNotFoundException {
		Medico medico = medicoRepository.findById(id).orElseThrow(MedicoNotFoundException::new);
		return atualizarCampos(medico, medicoForm);
	}
	
	public MedicoDTO atualizar(String crm, MedicoPutDTO medicoForm) throws MedicoNotFoundException {
		Medico medico = medicoRepository.findByCrm(crm).orElseThrow(MedicoNotFoundException::new);
		return atualizarCampos(medico, medicoForm);
	}
	
	public MedicoDTO atualizarCampos(Medico medico, MedicoPutDTO medicoForm) {
		medico.setNome(medicoForm.nome());
		medico.setTelefone(medicoForm.telefone());

		EnderecoPutDTO enderecoForm = new EnderecoPutDTO(medico.getEndereco(), medicoForm.endereco());
		Long endereco = enderecoClient.atualizar(enderecoForm).getBody();
		medico.setEndereco(endereco);
		
		medicoRepository.save(medico);
		return new MedicoDTO(medico);
	}

	@Override
	public void remover(Long id) throws EntityNotFoundException {
		Medico medico = medicoRepository.findById(id).orElseThrow(MedicoNotFoundException::new);
		tornarInativo(medico);
	}
	
	public void remover(String crm) throws EntityNotFoundException {
		Medico medico = medicoRepository.findByCrm(crm).orElseThrow(MedicoNotFoundException::new);
		tornarInativo(medico);
	}
	
	public void tornarInativo(Medico medico) {
		medico.setAtivo(false);
		medicoRepository.save(medico);
	}
	
	public Medico buscarMedicoIdAtivo(Long idMedico) throws EntityNotFoundException {
		return medicoRepository.findByIdAndAtivoTrue(idMedico).orElseThrow(MedicoNotFoundException::new);
	}
	
	public Medico buscarMedicoCrmAtivo(String crm) throws EntityNotFoundException {
		return medicoRepository.findByCrmAndAtivoTrue(crm).orElseThrow(MedicoNotFoundException::new);
	}
	
	public List<Long> buscarMedicosAtivosPorEspecialidade(Long idEspecialidade) {
		return medicoRepository.findAllIdsByAtivoTrueAndEspecialidade(idEspecialidade);
	}
}

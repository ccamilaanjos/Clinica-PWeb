package com.pweb.clinica.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pweb.clinica.dtos.MedicoDTO;
import com.pweb.clinica.dtos.MedicoFormDTO;
import com.pweb.clinica.models.Endereco;
import com.pweb.clinica.models.Medico;
import com.pweb.clinica.repositories.MedicoRepository;

@Service
public class MedicoService implements PessoaService<Medico, MedicoFormDTO, MedicoDTO> {

	@Autowired
	private MedicoRepository medicoRepository;
	@Autowired
	private EnderecoService enderecoService;

	@Override
	public Page<MedicoDTO> getPagina(Pageable pageable) {
		return medicoRepository.findAll(pageable).map(this::converterParaDTO);
	}

	@Override
	public MedicoDTO converterParaDTO(Medico medico) {
		return new MedicoDTO(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCRM(), medico.getEndereco(),
				medico.getCRM(), medico.getEspecialidade(), medico.getAtivo());
	}

	@Override
	public Medico cadastrar(MedicoFormDTO medicoForm) {
		Medico medico = new Medico();
		medico.setEmail(medicoForm.email());
		medico.setCRM(medicoForm.crm());
		medico.setEspecialidade(medicoForm.especialidade());
		
		salvarDados(medico, medicoForm);
		return medico;
	}
	
	@Override
	public Medico atualizar(Long id, MedicoFormDTO medicoForm) {
		Optional<Medico> optionalMedico = buscarPorID(id);
		if (optionalMedico.isEmpty()) {
			return null;
		}
		
		Medico medicoFound = optionalMedico.get();
		salvarDados(medicoFound, medicoForm);
		return medicoFound;
	}
	
	private Medico salvarDados(Medico medico, MedicoFormDTO medicoForm) {
		medico.setNome(medicoForm.nome() == null ? medico.getNome() : medicoForm.nome());
		medico.setTelefone(medicoForm.telefone() == null ? medico.getTelefone() : medicoForm.telefone());
		
		// Se todos os campos forem nulos, manter endereço antigo
		Endereco enderecoForm = enderecoService.converter(medicoForm.endereco());
		if(enderecoService.todosOsCamposSaoNulos(enderecoForm)) {
			medicoRepository.save(medico);
			return medico;
		}
		
		if (enderecoService.algumCampoNaoNuloENulo(enderecoForm)) {
			Endereco enderecoFinal = enderecoService.getEnderecoFinal(medico.getEndereco(), enderecoForm);
			medico.setEndereco(enderecoFinal);
			medicoRepository.save(medico);
			return medico;
		}
		
		if(enderecoService.algumCampoAnulavelENuloEOSDemaisNao(enderecoForm)) {
			Endereco enderecoFinal = enderecoService.getEnderecoFinalNulosNulos(medico.getEndereco(), enderecoForm);
			medico.setEndereco(enderecoFinal);
			medicoRepository.save(medico);
			return medico;
		}
		
		medico.setEndereco(atribuirEndereco(medico, enderecoForm));
		medicoRepository.save(medico);
		return medico;
	}
	
	private Endereco atribuirEndereco(Medico medico, Endereco enderecoForm) {
		Optional<Endereco> enderecoExistente = enderecoService.buscarEnderecoExistente(enderecoForm);
		
		if(enderecoExistente.isPresent()) {
			return enderecoExistente.get();
		}
		return enderecoService.getEnderecoFinal(medico.getEndereco(), enderecoForm);
	}

	@Override
	public Medico tornarInativo(Long id) {
		Optional<Medico> optionalMedico = buscarPorID(id);
		if (optionalMedico.isEmpty()) {
			return null;
		}

		Medico medico = optionalMedico.get();
		medico.setAtivo(false);
		medicoRepository.save(medico);

		return medico;
	}

	@Override
	public Optional<Medico> buscarPorID(Long id) {
		return medicoRepository.findById(id);
	}
}

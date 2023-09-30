package com.pweb.clinica.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pweb.clinica.dtos.MedicoDTO;
import com.pweb.clinica.dtos.MedicoPostDTO;
import com.pweb.clinica.dtos.MedicoPutDTO;
import com.pweb.clinica.exceptions.MedicoNotFoundException;
import com.pweb.clinica.models.Endereco;
import com.pweb.clinica.models.Medico;
import com.pweb.clinica.repositories.MedicoRepository;
import com.pweb.clinica.utils.converters.EnderecoConverter;
import com.pweb.clinica.utils.converters.MedicoConverter;

@Service
public class MedicoService implements PessoaService<Medico, MedicoPostDTO, MedicoPutDTO, MedicoDTO> {

	@Autowired
	private MedicoRepository medicoRepository;
	@Autowired
	private EnderecoService enderecoService;

	@Override
	public Page<MedicoDTO> getPagina(Pageable pageable) {
		return medicoRepository.findAll(pageable).map(MedicoConverter::converterParaDTO);
	}

	@Override
	public Medico cadastrar(MedicoPostDTO medicoForm) {
		Medico medico = new Medico();
		medico.setNome(medicoForm.nome());
		medico.setTelefone(medicoForm.telefone());
		medico.setEmail(medicoForm.email());
		medico.setCRM(medicoForm.crm());
		medico.setEspecialidade(medicoForm.especialidade());
		
		Endereco endereco = EnderecoConverter.converterDtoParaModel(medicoForm.endereco());
		medico.setEndereco(enderecoService.atribuirEndereco(endereco));
		medicoRepository.save(medico);
		
		return medico;
	}
	
	@Override
	public Medico atualizar(Long id, MedicoPutDTO medicoForm) throws MedicoNotFoundException {
		Optional<Medico> optionalMedico = buscarPorID(id);
		if (optionalMedico.isEmpty()) {
			throw new MedicoNotFoundException();
		}
		
		Medico medico = optionalMedico.get();

		medico.setNome(medicoForm.nome());
		medico.setTelefone(medicoForm.telefone());
		
		Endereco endereco = enderecoService.ajustarCampos(medico.getEndereco(), medicoForm.endereco());
		medico.setEndereco(enderecoService.atribuirEndereco(endereco));
		medicoRepository.save(medico);
		
		return medico;
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

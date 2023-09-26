package com.pweb.clinica.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pweb.clinica.dtos.MedicoDTO;
import com.pweb.clinica.dtos.MedicoFormDTO;
import com.pweb.clinica.models.Medico;
import com.pweb.clinica.repositories.MedicoRepository;

@Service
public class MedicoService implements PessoaService<MedicoFormDTO> {
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Override
	public List<MedicoDTO> converterLista(List<?> list) {
		return list.stream().map(medico -> new MedicoDTO((Medico) medico)).collect(Collectors.toList());
	}
	
	@Override
	public List<MedicoDTO> getListaOrdenadaPorNome() {
		return converterLista(medicoRepository.findAll(Sort.by(Sort.Direction.ASC, "nome")));
	}

	@Override
	public Medico cadastrar(MedicoFormDTO medicoForm) {
		Medico medico = new Medico();
		medico.setNome(medicoForm.nome());
		medico.setEmail(medicoForm.email());
		medico.setEndereco(medicoForm.endereco());
		medico.setTelefone(medicoForm.telefone());
		medico.setCRM(medicoForm.CRM());
		medico.setEspecialidade(medicoForm.especialidade());
		medicoRepository.save(medico);
		return medico;
	}

	@Override
	public Medico tornarInativo(Long id) {
		Optional<Medico> optionalMedico = buscarPorID(id);
		
		if(optionalMedico.isEmpty()) {
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

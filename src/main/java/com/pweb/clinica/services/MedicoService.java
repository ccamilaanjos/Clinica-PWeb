package com.pweb.clinica.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pweb.clinica.dtos.MedicoDTO;
import com.pweb.clinica.dtos.MedicoFormDTO;
import com.pweb.clinica.models.Medico;
import com.pweb.clinica.repositories.MedicoRepository;

@Service
public class MedicoService implements PessoaService<Medico, MedicoFormDTO, MedicoDTO> {

	@Autowired
	private MedicoRepository medicoRepository;

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

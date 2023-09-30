package com.pweb.clinica.dtos;

import com.pweb.clinica.converters.EnderecoConverter;
import com.pweb.clinica.enums.Especialidade;
import com.pweb.clinica.models.Medico;

public record MedicoDTO(Long id, String nome, String email, String telefone, EnderecoDTO endereco, String crm,
		Especialidade especialidade, Boolean ativo) {

	public MedicoDTO(Medico medico) {
		this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getTelefone(),
				EnderecoConverter.converterModelParaDTO(medico.getEndereco()), medico.getCRM(),
				medico.getEspecialidade(), medico.getAtivo());
	}
}

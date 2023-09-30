package com.pweb.clinica.dtos;

import com.pweb.clinica.models.Medico;
import com.pweb.clinica.utils.converters.EnderecoConverter;
import com.pweb.clinica.utils.converters.EspecialidadeConverter;

public record MedicoDTO(Long id, String nome, String email, String telefone, EnderecoDTO endereco, String crm,
		EspecialidadeDTO especialidade, Boolean ativo) {

	public MedicoDTO(Medico medico) {
		this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getTelefone(),
				EnderecoConverter.converterModelParaDTO(medico.getEndereco()), medico.getCRM(),
				EspecialidadeConverter.converterModelParaDTO(medico.getEspecialidade()), medico.getAtivo());
	}
}

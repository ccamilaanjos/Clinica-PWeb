package com.pweb.clinica.dtos;

import com.pweb.clinica.models.Medico;
import com.pweb.clinica.utils.converters.EnderecoConverter;

public record MedicoDTO(Long id, String nome, String email, String telefone, EnderecoDTO endereco, String crm,
		EspecialidadeDTO especialidade, Boolean ativo) {

	public MedicoDTO(Medico medico) {
		this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getTelefone(),
				new EnderecoDTO(medico.getEndereco()), medico.getCRM(),
				new EspecialidadeDTO(medico.getEspecialidade()), medico.getAtivo());
	}
}

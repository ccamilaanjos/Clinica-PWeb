package com.pweb.medico.dtos;

import com.pweb.medico.models.Medico;

public record MedicoDTO(Long id, String nome, String email, String telefone, Long endereco, String crm,
		Long especialidade, Boolean ativo) {

	public MedicoDTO(Medico medico) {
		this(medico.getId(),
				medico.getNome(),
				medico.getEmail(),
				medico.getTelefone(),
				medico.getEndereco(),
				medico.getCRM(),
				medico.getEspecialidade(),
				medico.getAtivo());
	}
}

package com.pweb.medico.dtos;

import com.pweb.medico.models.Medico;

public record MedicoConsultaDTO(Long id, String nome, String crm, Long especialidade) {
	public MedicoConsultaDTO(Medico medico) {
		this(medico.getId(), medico.getNome(), medico.getCRM(), medico.getEspecialidade());
	}
}

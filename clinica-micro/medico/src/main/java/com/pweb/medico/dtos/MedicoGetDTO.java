package com.pweb.medico.dtos;

import com.pweb.medico.models.Medico;

public record MedicoGetDTO(String nome, String email, String crm, Long especialidade) {
	
	public MedicoGetDTO(Medico medico, Long especialidade) {
		this(medico.getNome(), medico.getEmail(), medico.getCRM(), especialidade);
	}
}

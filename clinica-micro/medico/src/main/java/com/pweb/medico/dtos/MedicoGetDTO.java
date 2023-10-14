package com.pweb.medico.dtos;

import com.pweb.medico.models.Especialidade;
import com.pweb.medico.models.Medico;

public record MedicoGetDTO(String nome, String email, String crm, String especialidade) {
	
	public MedicoGetDTO(Medico medico, Especialidade especialidade) {
		this(medico.getNome(), medico.getEmail(), medico.getCRM(), new EspecialidadeDTO(especialidade).titulo());
	}
}

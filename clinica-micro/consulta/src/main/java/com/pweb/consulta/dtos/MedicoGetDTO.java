package com.pweb.consulta.dtos;

import com.pweb.consulta.clients.EspecialidadeDTO;
import com.pweb.consulta.clients.MedicoConsultaDTO;

public record MedicoGetDTO(String crm, String nome, String especialidade) {
	
	public MedicoGetDTO(MedicoConsultaDTO medico, EspecialidadeDTO especialidade) {
		this(medico.crm(), medico.nome(), especialidade.titulo());
	}
}

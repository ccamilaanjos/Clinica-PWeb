package com.pweb.medico.dtos;

import com.pweb.medico.models.Especialidade;

public record EspecialidadeDTO(Long id, String titulo) {
	
	public EspecialidadeDTO(Especialidade especialidade) {
		this(especialidade.getId(), especialidade.getTitulo());
	}
}

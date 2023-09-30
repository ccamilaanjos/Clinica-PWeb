package com.pweb.clinica.dtos;

import com.pweb.clinica.models.Especialidade;

public record EspecialidadeDTO(Long id, String descricao) {
	
	public EspecialidadeDTO(Especialidade especialidade) {
		this(especialidade.getId(), especialidade.getTitulo());
	}
}

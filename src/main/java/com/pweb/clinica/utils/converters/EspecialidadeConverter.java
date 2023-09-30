package com.pweb.clinica.utils.converters;

import com.pweb.clinica.dtos.EspecialidadeDTO;
import com.pweb.clinica.models.Especialidade;

public class EspecialidadeConverter {
	public static EspecialidadeDTO converterModelParaDTO(Especialidade especialidade) {
		return new EspecialidadeDTO(especialidade.getId(), especialidade.getTitulo());
	}
}

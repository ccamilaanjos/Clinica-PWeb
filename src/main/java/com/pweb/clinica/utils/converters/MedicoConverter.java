package com.pweb.clinica.utils.converters;

import com.pweb.clinica.dtos.MedicoDTO;
import com.pweb.clinica.models.Medico;

public class MedicoConverter {
	public static MedicoDTO converterParaDTO(Medico medico) {
		return new MedicoDTO(medico.getId(), medico.getNome(), medico.getEmail(), medico.getTelefone(),
				EnderecoConverter.converterModelParaDTO(medico.getEndereco()), medico.getCRM(),
				EspecialidadeConverter.converterModelParaDTO(medico.getEspecialidade()), medico.getAtivo());
	}
}

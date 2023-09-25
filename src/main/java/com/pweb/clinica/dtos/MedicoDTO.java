package com.pweb.clinica.dtos;

import com.pweb.clinica.models.Especialidade;
import com.pweb.clinica.models.Medico;

public record MedicoDTO (String nome, String email, String telefone/*, Endereco endereco*/, String CRM, Especialidade especialidade) {
	
	public MedicoDTO(Medico medico) {
		this(medico.getNome(), medico.getEmail(), medico.getTelefone(), medico.getCRM(), medico.getEspecialidade());
	}
}

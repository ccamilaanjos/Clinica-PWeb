package com.pweb.clinica.dtos;

import com.pweb.clinica.models.Endereco;
import com.pweb.clinica.models.Especialidade;
import com.pweb.clinica.models.Medico;

public record MedicoFormDTO(String nome, String email, String telefone, Endereco endereco, String crm, Especialidade especialidade) {

	public MedicoFormDTO(Medico medico) {
		this(medico.getNome(), medico.getEmail(), medico.getTelefone(), medico.getEndereco(), medico.getCRM(), medico.getEspecialidade());
	}
}

package com.pweb.clinica.dtos;

import com.pweb.clinica.models.Especialidade;
import com.pweb.clinica.models.Medico;

public record MedicoFormDTO(String nome, String email, String telefone, EnderecoFormDTO endereco, String crm, Especialidade especialidade) {

	public MedicoFormDTO(Medico medico, EnderecoFormDTO endereco) {
		this(medico.getNome(), medico.getEmail(), medico.getTelefone(), endereco, medico.getCRM(), medico.getEspecialidade());
	}
}

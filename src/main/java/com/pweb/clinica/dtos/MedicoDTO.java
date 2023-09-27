package com.pweb.clinica.dtos;

import com.pweb.clinica.models.Endereco;
import com.pweb.clinica.models.Especialidade;
import com.pweb.clinica.models.Medico;

public record MedicoDTO (Long id, String nome, String email, String telefone, Endereco endereco, String crm, Especialidade especialidade, Boolean ativo) {
	
	public MedicoDTO(Medico medico) {
		this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getTelefone(), medico.getEndereco(), medico.getCRM(), medico.getEspecialidade(), medico.getAtivo());
	}
}

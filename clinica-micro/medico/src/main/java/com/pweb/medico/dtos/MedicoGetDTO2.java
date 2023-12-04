package com.pweb.medico.dtos;

import com.pweb.medico.clients.EnderecoGetDTO;
import com.pweb.medico.models.Medico;

public record MedicoGetDTO2(String nome, String crm, String email, String telefone, String especialidade,
		EnderecoGetDTO endereco) {

	public MedicoGetDTO2(Medico medico, String especialidade, EnderecoGetDTO endereco) {
		this(medico.getNome(), medico.getCRM(), medico.getEmail(), medico.getTelefone(), especialidade, endereco);
	}
}

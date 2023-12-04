package com.pweb.paciente.dtos;

import com.pweb.paciente.clients.EnderecoGetDTO;
import com.pweb.paciente.models.Paciente;

public record PacienteGetDTO2(String nome, String cpf, String email, String telefone, EnderecoGetDTO endereco) {

	public PacienteGetDTO2(Paciente paciente, EnderecoGetDTO endereco) {
		this(paciente.getNome(), paciente.getCPF(), paciente.getEmail(), paciente.getTelefone(),
				endereco);
	}
}

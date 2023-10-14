package com.pweb.paciente.dtos;

import com.pweb.paciente.models.Paciente;

public record PacienteDTO(Long id, String nome, String cpf, String email, String telefone, Long endereco,
		Boolean ativo) {

	public PacienteDTO(Paciente paciente) {
		this(paciente.getId(), paciente.getNome(), paciente.getCPF(), paciente.getEmail(), paciente.getTelefone(),
				paciente.getEndereco(), paciente.getAtivo());
	}
}

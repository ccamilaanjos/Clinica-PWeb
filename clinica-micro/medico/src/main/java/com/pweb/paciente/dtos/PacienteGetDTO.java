package com.pweb.paciente.dtos;

import com.pweb.paciente.models.Paciente;

public record PacienteGetDTO(String nome, String cpf, String email) {

	public PacienteGetDTO(Paciente paciente) {
		this(paciente.getNome(), paciente.getCPF(), paciente.getEmail());
	}
}

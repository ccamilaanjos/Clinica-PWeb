package com.pweb.clinica.dtos;

import com.pweb.clinica.models.Paciente;

public record PacienteGetDTO(String nome, String cpf, String email) {

	public PacienteGetDTO(Paciente paciente) {
		this(paciente.getNome(), paciente.getCPF(), paciente.getEmail());
	}
}

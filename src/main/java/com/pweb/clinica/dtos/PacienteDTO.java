package com.pweb.clinica.dtos;

import com.pweb.clinica.models.Paciente;

public record PacienteDTO (String nome, String email, String telefone/*, Endereco endereco*/) {
	
	public PacienteDTO(Paciente paciente) {
		this(paciente.getNome(), paciente.getEmail(), paciente.getTelefone());
	}
}

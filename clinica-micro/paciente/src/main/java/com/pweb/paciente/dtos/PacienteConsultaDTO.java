package com.pweb.paciente.dtos;

import com.pweb.paciente.models.Paciente;

public record PacienteConsultaDTO(Long id, String nome, String email) {
	
	public PacienteConsultaDTO(Paciente paciente) {
		this(paciente.getId(), paciente.getNome(), paciente.getEmail());
	}
}

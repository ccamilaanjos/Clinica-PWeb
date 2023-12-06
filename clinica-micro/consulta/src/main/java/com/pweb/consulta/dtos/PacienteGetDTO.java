package com.pweb.consulta.dtos;

import com.pweb.consulta.clients.PacienteConsultaDTO;

public record PacienteGetDTO(String nome, String cpf, String email) {
	
	public PacienteGetDTO(PacienteConsultaDTO paciente) {
		this(paciente.nome(), paciente.cpf(), paciente.email());
	}
}

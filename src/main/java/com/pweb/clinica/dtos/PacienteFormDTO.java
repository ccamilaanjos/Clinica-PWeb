package com.pweb.clinica.dtos;

import com.pweb.clinica.models.Endereco;
import com.pweb.clinica.models.Paciente;

public record PacienteFormDTO (String nome, String email, String telefone, Endereco endereco) {
	
	public PacienteFormDTO(Paciente paciente) {
		this(paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getEndereco());
	}
}

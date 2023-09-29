package com.pweb.clinica.dtos;

import com.pweb.clinica.models.Paciente;

import jakarta.validation.constraints.NotNull;


public record PacientePutDTO (
		@NotNull(message = "O campo nome não pode ser nulo") String nome,
		@NotNull(message = "O campo telefone não pode ser nulo") String telefone,
		@NotNull(message = "O campo endereco não pode ser nulo") EnderecoFormDTO endereco) {
	
	public PacientePutDTO(Paciente paciente, EnderecoFormDTO endereco) {
		this(paciente.getNome(), paciente.getTelefone(), endereco);
	}
}

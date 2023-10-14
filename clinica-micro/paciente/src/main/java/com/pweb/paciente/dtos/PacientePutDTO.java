package com.pweb.paciente.dtos;

import com.pweb.paciente.clients.EnderecoPostDTO;
import com.pweb.paciente.models.Paciente;

import jakarta.validation.constraints.NotNull;


public record PacientePutDTO (
		@NotNull(message = "O campo nome não pode ser nulo") String nome,
		@NotNull(message = "O campo telefone não pode ser nulo") String telefone,
		@NotNull(message = "O campo endereco não pode ser nulo") EnderecoPostDTO endereco) {
	
	public PacientePutDTO(Paciente paciente, EnderecoPostDTO endereco) {
		this(paciente.getNome(), paciente.getTelefone(), endereco);
	}
}

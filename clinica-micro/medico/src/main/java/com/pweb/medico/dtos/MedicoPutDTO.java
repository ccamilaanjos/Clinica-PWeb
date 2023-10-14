package com.pweb.medico.dtos;

import com.pweb.medico.clients.EnderecoPostDTO;
import com.pweb.medico.models.Medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record MedicoPutDTO(
		@NotNull(message = "O campo nome não pode ser nulo") String nome,
		@NotNull(message = "O campo telefone não pode ser nulo") String telefone,
		@Valid EnderecoPostDTO endereco) {

	public MedicoPutDTO(Medico medico, EnderecoPostDTO endereco) {
		this(medico.getNome(), medico.getTelefone(), endereco);
	}
}

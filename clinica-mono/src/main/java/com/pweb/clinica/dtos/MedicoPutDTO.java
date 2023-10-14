package com.pweb.clinica.dtos;

import com.pweb.clinica.models.Medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record MedicoPutDTO(
		@NotNull(message = "O campo nome não pode ser nulo") String nome,
		@NotNull(message = "O campo telefone não pode ser nulo") String telefone,
		@Valid EnderecoFormDTO endereco) {

	public MedicoPutDTO(Medico medico, EnderecoFormDTO endereco) {
		this(medico.getNome(), medico.getTelefone(), endereco);
	}
}

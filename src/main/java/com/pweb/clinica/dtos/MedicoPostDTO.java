package com.pweb.clinica.dtos;

import com.pweb.clinica.models.Especialidade;
import com.pweb.clinica.models.Medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MedicoPostDTO(
		@NotBlank(message = "O campo nome não pode ser nulo ou vazio") String nome,
		@NotBlank(message = "O campo email não pode ser nulo ou vazio") String email,
		@NotBlank(message = "O campo telefone não pode ser nulo ou vazio") String telefone,
		@NotBlank(message = "O campo crm não pode ser nulo ou vazio") String crm,
		@NotNull(message = "O campo especialidade não pode ser nulo") Especialidade especialidade,
		@Valid EnderecoFormDTO endereco) {

	public MedicoPostDTO(Medico medico, EnderecoFormDTO endereco) {
		this(medico.getNome(), medico.getEmail(), medico.getTelefone(), medico.getCRM(),
				medico.getEspecialidade(), endereco);
	}
}

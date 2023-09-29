package com.pweb.clinica.dtos;

import com.pweb.clinica.models.Paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record PacientePostDTO (
		@NotBlank(message = "O campo nome não pode ser nulo ou vazio") String nome,
		@NotBlank(message = "O campo email não pode ser nulo ou vazio") String email,
		@NotBlank(message = "O campo telefone não pode ser nulo ou vazio") String telefone,
		@NotBlank(message = "O campo cpf não pode ser nulo ou vazio") String cpf,
		@Valid EnderecoFormDTO endereco) { 
	
	public PacientePostDTO(Paciente paciente, EnderecoFormDTO endereco) {
		this(paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getCPF(), endereco);
	}
}

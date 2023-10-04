package com.pweb.clinica.dtos;

import org.hibernate.validator.constraints.br.CPF;

import com.pweb.clinica.models.Paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record PacientePostDTO (
		@NotBlank(message = "O campo nome não pode ser nulo ou vazio") String nome,
		@NotBlank(message = "O campo email não pode ser nulo ou vazio") String email,
		@NotBlank(message = "O campo telefone não pode ser nulo ou vazio") String telefone,
		@CPF String cpf,
		@Valid EnderecoFormDTO endereco) { 
	
	public PacientePostDTO(Paciente paciente, EnderecoFormDTO endereco) {
		this(paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getCPF(), endereco);
	}
}

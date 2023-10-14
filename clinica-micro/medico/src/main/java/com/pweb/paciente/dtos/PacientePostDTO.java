package com.pweb.paciente.dtos;

import org.hibernate.validator.constraints.br.CPF;

import com.pweb.paciente.clients.EnderecoPostDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PacientePostDTO (
		@NotBlank(message = "O campo nome não pode ser nulo ou vazio") String nome,
		@NotBlank(message = "O campo email não pode ser nulo ou vazio") String email,
		@NotBlank(message = "O campo telefone não pode ser nulo ou vazio") String telefone,
		@CPF @NotNull String cpf,
		@Valid EnderecoPostDTO endereco) {}

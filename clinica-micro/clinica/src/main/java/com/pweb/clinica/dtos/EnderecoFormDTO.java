package com.pweb.clinica.dtos;

import com.pweb.clinica.models.Endereco;

import jakarta.validation.constraints.NotNull;

public record EnderecoFormDTO(
		@NotNull(message = "O campo logradouro não pode ser nulo") String logradouro,
		String numero,
		String complemento,
		@NotNull(message = "O campo bairro não pode ser nulo") String bairro,
		@NotNull(message = "O campo cidade não pode ser nulo") String cidade,
		@NotNull(message = "O campo uf não pode ser nulo") String uf,
		@NotNull(message = "O campo cep não pode ser nulo") String cep) {
	public EnderecoFormDTO(Endereco endereco) {
		this(endereco.getLogradouro().toUpperCase(), endereco.getNumero(), endereco.getComplemento().toUpperCase(), endereco.getBairro().toUpperCase(),
				endereco.getCidade().toUpperCase(), endereco.getUF().toUpperCase(), endereco.getCep().toUpperCase());
	}
}

package com.pweb.clinica.dtos;

import com.pweb.clinica.models.Endereco;

public record EnderecoFormDTO(String logradouro, Integer numero, String complemento, String bairro, String cidade,
		String uf, String cep) {
	public EnderecoFormDTO(Endereco endereco) {
		this(endereco.getLogradouro(), endereco.getNumero(), endereco.getComplemento(), endereco.getBairro(),
				endereco.getCidade(), endereco.getUF(), endereco.getCep());
	}
}

package com.pweb.clinica.dtos;

import com.pweb.clinica.models.Endereco;

public record EnderecoFormDTO(String logradouro, Integer numero, String complemento, String bairro, String cidade,
		String uf, String cep) {
	public EnderecoFormDTO(Endereco endereco) {
		this(endereco.getLogradouro().toUpperCase(), endereco.getNumero(), endereco.getComplemento().toUpperCase(), endereco.getBairro().toUpperCase(),
				endereco.getCidade().toUpperCase(), endereco.getUF().toUpperCase(), endereco.getCep().toUpperCase());
	}
}

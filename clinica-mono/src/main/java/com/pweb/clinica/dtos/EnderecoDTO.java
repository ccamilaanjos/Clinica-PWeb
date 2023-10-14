package com.pweb.clinica.dtos;

import com.pweb.clinica.models.Endereco;

public record EnderecoDTO(Long id, String logradouro, String numero, String complemento, String bairro, String cidade,
		String uf, String cep) {

	public EnderecoDTO(Endereco endereco) {
		this(endereco.getId(), endereco.getLogradouro().toUpperCase(), endereco.getNumero(), endereco.getComplemento().toUpperCase(),
				endereco.getBairro().toUpperCase(), endereco.getCidade().toUpperCase(), endereco.getUF().toUpperCase(),
				endereco.getCep().toUpperCase());
	}
}

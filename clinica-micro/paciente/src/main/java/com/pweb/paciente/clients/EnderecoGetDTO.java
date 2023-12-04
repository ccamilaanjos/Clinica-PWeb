package com.pweb.paciente.clients;

public record EnderecoGetDTO(String logradouro, String numero, String complemento, String bairro, String cidade,
		String uf, String cep) {
}

package com.pweb.clinica.converters;

import com.pweb.clinica.dtos.EnderecoFormDTO;
import com.pweb.clinica.models.Endereco;

public class EnderecoConverter {

	public static Endereco converterDtoParaModel(EnderecoFormDTO enderecoForm) {
		Endereco endereco = new Endereco();

		endereco.setLogradouro(enderecoForm.logradouro().toUpperCase());
		endereco.setNumero(
				enderecoForm.numero() != null ? enderecoForm.numero() == "" ? "s/n" : enderecoForm.numero() : "s/n");
		endereco.setComplemento(enderecoForm.complemento() != null
				? enderecoForm.complemento() == "" ? null : enderecoForm.complemento().toUpperCase()
				: null);
		endereco.setBairro(enderecoForm.bairro().toUpperCase());
		endereco.setCidade(enderecoForm.cidade().toUpperCase());
		endereco.setUF(enderecoForm.uf().toUpperCase());
		endereco.setCep(enderecoForm.cep());

		return endereco;
	}
}

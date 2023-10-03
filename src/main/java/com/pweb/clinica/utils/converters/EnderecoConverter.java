package com.pweb.clinica.utils.converters;

import com.pweb.clinica.dtos.EnderecoFormDTO;
import com.pweb.clinica.models.Endereco;

public class EnderecoConverter {

	public static Endereco converterDtoParaModel(EnderecoFormDTO enderecoForm) {
		Endereco endereco = new Endereco();

		endereco.setLogradouro(enderecoForm.logradouro());
		endereco.setNumero(enderecoForm.numero());
		endereco.setComplemento(enderecoForm.complemento());
		endereco.setBairro(enderecoForm.bairro());
		endereco.setCidade(enderecoForm.cidade());
		endereco.setUF(enderecoForm.uf());
		endereco.setCep(enderecoForm.cep());

		return endereco;
	}
	
	public static String refinarLogradouro(String logradouro) {
		return logradouro.toUpperCase();
	}
	
	public static String refinarNumero(String numero) {
		if(numero == null || numero.equalsIgnoreCase("s/n") || numero.equals("")) {
			return "s/n";
		}
		return numero;
	}
	
	public static String refinarComplemento(String complemento) {
		if(complemento == null || complemento.equals("")) {
			return "";
		}
		return complemento.toUpperCase();
	}
	
	public static String refinarBairro(String bairro) {
		return bairro.toUpperCase();
	}
	
	public static String refinarCidade(String cidade) {
		return cidade.toUpperCase();
	}
	
	public static String refinarUF(String uf) {
		return uf.toUpperCase();
	}
	
	public static String refinarCep(String cep) {
		return cep;
	}
}

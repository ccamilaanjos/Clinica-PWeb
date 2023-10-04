package com.pweb.clinica.utils.converters;

public class EnderecoConverter {
	
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

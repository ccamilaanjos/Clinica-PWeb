package com.pweb.endereco.exceptions;

public class EnderecoNotFoundException extends RuntimeException {
	
	public EnderecoNotFoundException() {
		super("Endereço não encontrado");
	}
}

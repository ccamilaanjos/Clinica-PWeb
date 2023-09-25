package com.pweb.clinica.models;

import jakarta.persistence.Entity;

@Entity(name="pacientes")
public class Paciente extends Pessoa {

	public Paciente() {
		super();
	}
	
	public Paciente(String nome, String email, /*Endereco endereco,*/ String telefone) {
		super(nome, email, telefone/*, endereco*/);
	}
}

package com.pweb.clinica.models;

import jakarta.persistence.Entity;

@Entity(name="pacientes")
public class Paciente extends Pessoa {
	private String CPF;

	public Paciente() {
		super();
	}
	
	public Paciente(String nome, String email, String telefone, Endereco endereco, Boolean ativo, String CPF) {
		super(nome, email, telefone, endereco, ativo);
		this.CPF = CPF;
	}
	
	public String getCPF() {
		return CPF;
	}
	
	public void setCPF(String CPF) {
		this.CPF = CPF;
	}
}

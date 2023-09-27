package com.pweb.clinica.models;

import jakarta.persistence.Entity;

@Entity(name="pacientes")
public class Paciente extends Pessoa {
	private String cpf;

	public Paciente() {
		super();
	}
	
	public Paciente(String nome, String email, String telefone, Endereco endereco, String cpf) {
		super(nome, email, telefone, endereco);
		this.cpf = cpf;
	}
	
	public String getCPF() {
		return cpf;
	}
	
	public void setCPF(String cpf) {
		this.cpf = cpf;
	}
}

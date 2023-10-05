package com.pweb.clinica.models;

import com.pweb.clinica.dtos.PacientePostDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity(name="pacientes")
public class Paciente extends Pessoa {
	@Column(unique = true)
	private String cpf;

	public Paciente() {
		super();
	}
	
	public Paciente(PacientePostDTO paciente, Endereco endereco) {
		super.setNome(paciente.nome());
		super.setEmail(paciente.email());
		this.cpf = paciente.cpf();
		super.setTelefone(paciente.telefone());
		super.setEndereco(endereco);
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

	@Override
	public String toString() {
		return "Paciente [cpf=" + cpf + "]";
	}
}

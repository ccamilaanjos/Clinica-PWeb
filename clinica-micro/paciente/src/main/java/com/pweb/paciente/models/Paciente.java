package com.pweb.paciente.models;

import com.pweb.paciente.dtos.PacientePostDTO;
import com.pweb.pessoa.models.Pessoa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity(name="pacientes")
public class Paciente extends Pessoa {
	@Column(unique = true)
	private String cpf;

	public Paciente() {
		super();
	}
	
	public Paciente(PacientePostDTO paciente, Long endereco) {
		super.setNome(paciente.nome());
		super.setEmail(paciente.email());
		this.cpf = paciente.cpf();
		super.setTelefone(paciente.telefone());
		super.setEndereco(endereco);
	}
	
	public Paciente(String nome, String email, String telefone, Long endereco, String cpf) {
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

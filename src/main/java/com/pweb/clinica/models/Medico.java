package com.pweb.clinica.models;

import com.pweb.clinica.enums.Especialidade;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity(name="medicos")
public class Medico extends Pessoa {
	private String crm;
	@Enumerated(EnumType.STRING)
	private Especialidade especialidade;
	
	public Medico() {
		super();
	}
	
	public Medico(String nome, String email, String telefone, Endereco endereco, String crm, Especialidade especialidade) {
		super(nome, email, telefone, endereco);
		this.crm = crm;
		this.especialidade = especialidade;
	}

	public String getCRM() {
		return crm;
	}

	public void setCRM(String crm) {
		this.crm = crm;
	}

	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}
	
}

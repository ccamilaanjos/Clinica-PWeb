package com.pweb.clinica.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity(name="medicos")
public class Medico extends Pessoa {
	private String CRM;
	@Enumerated(EnumType.STRING)
	private Especialidade especialidade;
	
	public Medico() {
		super();
	}
	
	public Medico(String nome, String email, String telefone, Endereco endereco, String CRM, Especialidade especialidade, Boolean ativo) {
		super(nome, email, telefone, endereco, ativo);
		this.CRM = CRM;
		this.especialidade = especialidade;
	}

	@Override
	public void setTelefone(String telefone) {
		super.setTelefone(telefone);
	}

	public String getCRM() {
		return CRM;
	}

	public void setCRM(String cRM) {
		CRM = cRM;
	}

	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}
	
}

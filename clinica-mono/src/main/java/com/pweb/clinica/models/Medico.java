package com.pweb.clinica.models;

import com.pweb.clinica.dtos.MedicoPostDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name="medicos")
public class Medico extends Pessoa {
	@Column(unique = true)
	private String crm;
//	@Enumerated(EnumType.STRING)
//	private EspecialidadeTipo especialidade;
	@ManyToOne
	@JoinColumn(name="especialidade_id")
	private Especialidade especialidade;
	
	public Medico() {
		super();
	}
	
	public Medico(String nome, String email, String telefone, Endereco endereco, String crm, Especialidade especialidade) {
		super(nome, email, telefone, endereco);
		this.crm = crm;
		this.especialidade = especialidade;
	}
	
	public Medico(MedicoPostDTO medico, Especialidade especialidade, Endereco endereco) {
		this.especialidade = especialidade;
		this.crm = medico.crm();
		super.setNome(medico.nome());
		super.setTelefone(medico.telefone());
		super.setEmail(medico.email());
		super.setEndereco(endereco);
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

	@Override
	public String toString() {
		return "Medico [crm=" + crm + ", especialidade=" + especialidade + "]";
	}
	
	
}

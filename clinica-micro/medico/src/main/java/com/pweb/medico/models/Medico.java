package com.pweb.medico.models;

import com.pweb.medico.dtos.MedicoPostDTO;
import com.pweb.pessoa.models.Pessoa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity(name="medicos")
public class Medico extends Pessoa {
	@Column(unique = true)
	private String crm;
//	@Enumerated(EnumType.STRING)
//	private EspecialidadeTipo especialidade;
	@Column(nullable=false)
	private Long especialidade;
	
	public Medico() {
		super();
	}
	
	public Medico(MedicoPostDTO medico, Long especialidade, Long endereco) {
		this.especialidade = especialidade;
		this.crm = medico.crm();
		super.setNome(medico.nome());
		super.setTelefone(medico.telefone());
		super.setEmail(medico.email());
		super.setEndereco(endereco);
	}
	
	public Medico(String nome, String email, String telefone, Long endereco, String crm, Long especialidade) {
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

	public Long getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Long especialidade) {
		this.especialidade = especialidade;
	}

	@Override
	public String toString() {
		return "Medico [crm=" + crm + ", especialidade=" + especialidade + "]";
	}	
}

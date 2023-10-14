package com.pweb.pessoa.models;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class Pessoa {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	private String telefone;
	@Column(nullable=false)
	private Long endereco_id;
	@ColumnDefault(value = "TRUE")
	private Boolean ativo = true;
	
	public Pessoa() {}

	public Pessoa(String nome, String email, String telefone, Long endereco) {
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.endereco_id = endereco;
	}
	
	public Long getId() {
		return id;
	}
		
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Long getEndereco() {
		return endereco_id;
	}

	public void setEndereco(Long endereco) {
		this.endereco_id = endereco;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
}

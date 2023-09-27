package com.pweb.clinica.models;

import com.pweb.clinica.dtos.EnderecoFormDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name="enderecos")
public class Endereco {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(nullable=false)
	private String logradouro;
	private Integer numero;
	private String complemento;
	@Column(nullable=false)
	private String bairro;
	@Column(nullable=false)
	private String cidade;
	@Column(nullable=false)
	private String uf;
	@Column(nullable=false)
	private String cep;
	
	public Endereco() {}
	
	public Endereco(String logradouro, int numero, String complemento, String bairro, String cidade, String uf,
			String cep) {
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cidade = cidade;
		this.uf = uf;
		this.cep = cep;
	}
	
	public Endereco(EnderecoFormDTO enderecoForm) {
		this.logradouro = enderecoForm.logradouro();
		this.numero = enderecoForm.numero();
		this.complemento = enderecoForm.complemento();
		this.bairro = enderecoForm.bairro();
		this.cidade = enderecoForm.cidade();
		this.uf = enderecoForm.uf();
		this.cep = enderecoForm.cep();
	}
	
	public Long getId() {
		return id;
	}
	
	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro == null ? logradouro : logradouro.toUpperCase();
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento == null ? complemento : complemento.toUpperCase();
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro == null ? bairro : bairro.toUpperCase();
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade == null ? cidade : cidade.toUpperCase();
	}

	public String getUF() {
		return uf;
	}

	public void setUF(String uf) {
		this.uf =  uf == null ? uf : uf.toUpperCase();
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
}

package com.pweb.clinica.models;

import com.pweb.clinica.converters.EnderecoConverter;

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
	private String numero;
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
	
	public Endereco(String logradouro, String numero, String complemento, String bairro, String cidade, String uf,
			String cep) {
		this.logradouro = EnderecoConverter.refinarLogradouro(logradouro);
		this.numero = EnderecoConverter.refinarNumero(numero);
		this.complemento = EnderecoConverter.refinarComplemento(complemento);
		this.bairro = EnderecoConverter.refinarBairro(bairro);
		this.cidade = EnderecoConverter.refinarCidade(cidade);
		this.uf = EnderecoConverter.refinarUF(uf);
		this.cep = EnderecoConverter.refinarCep(cep);
	}
	
	public Long getId() {
		return id;
	}
	
	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = EnderecoConverter.refinarLogradouro(logradouro);
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = EnderecoConverter.refinarNumero(numero);
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = EnderecoConverter.refinarComplemento(complemento);
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = EnderecoConverter.refinarBairro(bairro);
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = EnderecoConverter.refinarCidade(cidade);
	}

	public String getUF() {
		return uf;
	}

	public void setUF(String uf) {
		this.uf = EnderecoConverter.refinarUF(uf);
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = EnderecoConverter.refinarCep(cep);
	}
}

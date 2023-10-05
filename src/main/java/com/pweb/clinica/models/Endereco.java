package com.pweb.clinica.models;

import com.pweb.clinica.dtos.EnderecoFormDTO;
// import com.pweb.clinica.utils.converters.EnderecoConverter;

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
		this.logradouro = refinarLogradouro(logradouro);
		this.numero = refinarNumero(numero);
		this.complemento = refinarComplemento(complemento);
		this.bairro = refinarBairro(bairro);
		this.cidade = refinarCidade(cidade);
		this.uf = refinarUF(uf);
		this.cep = cep;
	}
	
	public Endereco(EnderecoFormDTO endereco) {
		this.logradouro = endereco.logradouro();
		this.numero = endereco.numero();
		this.complemento = endereco.complemento();
		this.bairro = endereco.bairro();
		this.cidade = endereco.cidade();
		this.uf = endereco.uf();
		this.cep = endereco.cep();
	}
	
	public Long getId() {
		return id;
	}
	
	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = refinarLogradouro(logradouro);
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = refinarNumero(numero);
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = refinarComplemento(complemento);
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = refinarBairro(bairro);
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = refinarCidade(cidade);
	}

	public String getUF() {
		return uf;
	}

	public void setUF(String uf) {
		this.uf = refinarUF(uf);
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
	
	/*
	 * Os métodos abaixo garantem que o endereço seja persistido em upper case,
	 * para facilitar a comparação entre os campos ao verificar se um dado endereço
	 * já existe. 
	 */
	
	public String refinarLogradouro(String logradouro) {
		return logradouro.toUpperCase();
	}

	public String refinarNumero(String numero) {
		if(numero == null || numero.equalsIgnoreCase("s/n") || numero.equals("")) {
			return "s/n";
		}
		return numero;
	}

	public String refinarComplemento(String complemento) {
		if(complemento == null || complemento.equals("")) {
			return "";
		}
		return complemento.toUpperCase();
	}

	public String refinarBairro(String bairro) {
		return bairro.toUpperCase();
	}

	public  String refinarCidade(String cidade) {
		return cidade.toUpperCase();
	}

	public String refinarUF(String uf) {
		return uf.toUpperCase();
	}
}
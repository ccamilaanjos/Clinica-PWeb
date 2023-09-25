//package com.pweb.clinica.models;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.OneToOne;
//
//@Entity(name="enderecos")
//public class Endereco {
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	private Long id;
//	//@OneToOne
//	// private Medico pessoa;
//	private String logradouro;
//	private int numero;
//	private String complemento;
//	private String bairro;
//	private String cidade;
//	private String UF;
//	private String CEP;
//	
//	public Endereco(String logradouro, int numero, String complemento, String bairro, String cidade, String UF,
//			String CEP) {
//		this.logradouro = logradouro;
//		this.numero = numero;
//		this.complemento = complemento;
//		this.bairro = bairro;
//		this.cidade = cidade;
//		this.UF = UF;
//		this.CEP = CEP;
//	}
//	
////	public Pessoa getPessoa() {
////		return pessoa;
////	}
////	
////	public void setPessoa(Pessoa pessoa) {
////		this.pessoa = pessoa;
////	}
//
//	public String getLogradouro() {
//		return logradouro;
//	}
//
//	public void setLogradouro(String logradouro) {
//		this.logradouro = logradouro;
//	}
//
//	public int getNumero() {
//		return numero;
//	}
//
//	public void setNumero(int numero) {
//		this.numero = numero;
//	}
//
//	public String getComplemento() {
//		return complemento;
//	}
//
//	public void setComplemento(String complemento) {
//		this.complemento = complemento;
//	}
//
//	public String getBairro() {
//		return bairro;
//	}
//
//	public void setBairro(String bairro) {
//		this.bairro = bairro;
//	}
//
//	public String getCidade() {
//		return cidade;
//	}
//
//	public void setCidade(String cidade) {
//		this.cidade = cidade;
//	}
//
//	public String getUF() {
//		return UF;
//	}
//
//	public void setUF(String UF) {
//		this.UF = UF;
//	}
//
//	public String getCEP() {
//		return CEP;
//	}
//
//	public void setCEP(String CEP) {
//		this.CEP = CEP;
//	}
//}

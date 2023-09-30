package com.pweb.clinica.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pweb.clinica.converters.EnderecoConverter;
import com.pweb.clinica.dtos.EnderecoFormDTO;
import com.pweb.clinica.models.Endereco;
import com.pweb.clinica.repositories.EnderecoRepository;

@Service
public class EnderecoService {
	@Autowired
	private EnderecoRepository enderecoRepository;

	public Optional<Endereco> buscarEnderecoExistente(Endereco endereco) {
		return enderecoRepository.findByLogradouroAndNumeroAndComplementoAndBairroAndCidadeAndUfAndCepIgnoreCase(
				endereco.getLogradouro(), endereco.getNumero(), endereco.getComplemento(), endereco.getBairro(),
				endereco.getCidade(), endereco.getUF(), endereco.getCep());
	}
	
	public Endereco atribuirEndereco(Endereco enderecoForm) {
		Optional<Endereco> enderecoExistente = buscarEnderecoExistente(enderecoForm);
		if(enderecoExistente.isPresent()) {
			return enderecoExistente.get();
		}

		return enderecoForm;
	}
	
	public Endereco ajustarCampos(Endereco enderecoAntigo, EnderecoFormDTO enderecoForm) {
		Endereco endereco = EnderecoConverter.converterDtoParaModel(enderecoForm);
		Endereco enderecoFinal = new Endereco();

		// Se os campos obrigatórios forem uma String vazia, manter registro antigo
		enderecoFinal.setLogradouro(
				endereco.getLogradouro() == "" ? enderecoAntigo.getLogradouro() : endereco.getLogradouro());
		enderecoFinal
				.setBairro(endereco.getBairro() == "" ? enderecoAntigo.getBairro() : endereco.getBairro());
		enderecoFinal
				.setCidade(endereco.getCidade() == "" ? enderecoAntigo.getCidade() : endereco.getCidade());
		enderecoFinal.setUF(endereco.getUF() == "" ? enderecoAntigo.getUF() : endereco.getUF());
		enderecoFinal.setCep(endereco.getCep() == "" ? enderecoAntigo.getCep() : endereco.getCep());

		// Campos opcionais:
		// Se o número for uma String vazia, definir como campo "s/n"
		enderecoFinal.setNumero(endereco.getNumero() == "" ? "s/n" : endereco.getNumero());
		// Se o complemento for null ou String vazia, definir como String vazia
		enderecoFinal.setComplemento(endereco.getComplemento() == null ? "" : endereco.getComplemento());
		
		return enderecoFinal;
	}
}

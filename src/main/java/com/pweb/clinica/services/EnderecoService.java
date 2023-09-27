package com.pweb.clinica.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pweb.clinica.models.Endereco;
import com.pweb.clinica.repositories.EnderecoRepository;

@Service
public class EnderecoService {
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public EnderecoService() {}
	
	public Optional<Endereco> buscarEnderecoExistente(Endereco endereco) {
	    return this.enderecoRepository.findByLogradouroAndNumeroAndComplementoAndBairroAndCidadeAndUfAndCepIgnoreCase(
	    		endereco.getLogradouro(),
	    		endereco.getNumero(),
	    		endereco.getComplemento(),
	    		endereco.getBairro(),
	    		endereco.getCidade(),
	    		endereco.getUF(),
	    		endereco.getCep()
	    );
	}
}

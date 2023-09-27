package com.pweb.clinica.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pweb.clinica.dtos.EnderecoFormDTO;
import com.pweb.clinica.models.Endereco;
import com.pweb.clinica.repositories.EnderecoRepository;

@Service
public class EnderecoService {
	@Autowired
	private EnderecoRepository enderecoRepository;
	
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
	
	public Endereco getEnderecoFinal(Endereco enderecoAntigo, EnderecoFormDTO enderecoForm) {
		Endereco enderecoFinal = new Endereco();
		
		enderecoFinal.setLogradouro(enderecoForm.logradouro() == null ? enderecoAntigo.getLogradouro() : enderecoForm.logradouro());
		enderecoFinal.setNumero(enderecoForm.numero() == null ? enderecoAntigo.getNumero() : enderecoForm.numero());
		enderecoFinal.setComplemento(enderecoForm.complemento() == null ? enderecoAntigo.getComplemento() : enderecoForm.complemento());
		enderecoFinal.setBairro(enderecoForm.bairro() == null ? enderecoAntigo.getBairro() : enderecoForm.bairro());
		enderecoFinal.setCidade(enderecoForm.cidade() == null ? enderecoAntigo.getCidade() : enderecoForm.cidade());
		enderecoFinal.setUF(enderecoForm.uf() == null ? enderecoAntigo.getUF() : enderecoForm.uf());
		enderecoFinal.setCep(enderecoForm.cep() == null ? enderecoAntigo.getCep() : enderecoForm.cep());
		
		return enderecoFinal;
	}
}

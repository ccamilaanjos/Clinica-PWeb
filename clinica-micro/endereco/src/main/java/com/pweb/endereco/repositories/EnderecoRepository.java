package com.pweb.endereco.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pweb.endereco.models.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long>{
	public Optional<Endereco> findByLogradouroAndNumeroAndComplementoAndBairroAndCidadeAndUfAndCepIgnoreCase(
			String logradouro, String numero, String complemento, String bairro, String cidade, String uf, String cep);
	
}

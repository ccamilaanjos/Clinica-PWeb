package com.pweb.clinica.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pweb.clinica.models.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long>{
	Optional<Endereco> findByLogradouroAndNumeroAndComplementoAndBairroAndCidadeAndUfAndCepIgnoreCase(
			String logradouro, String numero, String complemento, String bairro, String cidade, String uf, String cep);
	
}

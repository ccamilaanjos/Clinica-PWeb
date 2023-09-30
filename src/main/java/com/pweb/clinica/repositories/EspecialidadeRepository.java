package com.pweb.clinica.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pweb.clinica.models.Especialidade;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long>{
	public Optional<Especialidade> findByTituloIgnoreCase(String titulo);
}

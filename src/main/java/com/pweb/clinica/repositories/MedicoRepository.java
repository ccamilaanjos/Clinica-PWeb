package com.pweb.clinica.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pweb.clinica.models.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
	public Optional<Medico> findByCrm(String crm);
	public Optional<List<Medico>> findByEspecialidade_idOrderByNomeAsc(Long id);
	public Optional<List<Medico>> findByEspecialidade_idAndAtivoTrueOrderByNomeAsc(Long id);
}

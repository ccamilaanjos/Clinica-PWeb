package com.pweb.medico.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pweb.medico.models.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
	public Optional<Medico> findByCrm(String crm);
	public Optional<Medico> findByIdAndAtivoTrue(Long id);
	public Page<Medico> findAllByAtivoTrue(Pageable pageable);
	public Optional<List<Medico>> findByEspecialidadeOrderByNomeAsc(Long especialidade);
	public Optional<List<Medico>> findByEspecialidadeAndAtivoTrueOrderByNomeAsc(Long especialidade);
	
	@Query("SELECT m.id FROM medicos m "
			+ "WHERE m.ativo = TRUE "
			+ "AND m.especialidade = :especialidade")
	public List<Long> findAllIdsByAtivoTrueAndEspecialidade(@Param("especialidade") Long especialidade);
	
}
